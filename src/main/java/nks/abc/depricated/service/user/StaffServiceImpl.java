package nks.abc.depricated.service.user;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import nks.abc.core.exception.handler.ErrorHandler;
import nks.abc.core.exception.repository.RepositoryException;
import nks.abc.core.exception.service.NoCurrentUserException;
import nks.abc.core.exception.service.NoIdException;
import nks.abc.core.exception.service.NoUserLoginException;
import nks.abc.core.exception.service.RightsDeprivingException;
import nks.abc.core.exception.service.SendMailException;
import nks.abc.core.exception.service.ServiceDisplayedErorr;
import nks.abc.core.exception.service.ServiceException;
import nks.abc.dao.base.interfaces.CriterionSpecification;
import nks.abc.dao.newspecification.user.AccountSpecifications;
import nks.abc.dao.newspecification.user.UserSpecifications;
import nks.abc.dao.repository.user.AccountRepository;
import nks.abc.dao.repository.user.AdminRepository;
import nks.abc.dao.repository.user.TeacherRepository;
import nks.abc.dao.specification.user.account.AccountSpecificationFactory;
import nks.abc.depricated.service.message.MailFactory;
import nks.abc.depricated.service.message.MailService;
//import nks.abc.depricated.view.converter.user.AccountViewConverter;
import nks.abc.depricated.view.object.objects.user.StaffView;
import nks.abc.domain.user.Account;
import nks.abc.domain.user.Administrator;
import nks.abc.domain.user.PersonalInfo;
import nks.abc.domain.user.Staff;
import nks.abc.domain.user.Teacher;
import nks.abc.domain.user.User;
import nks.abc.domain.user.factory.AccountFactory;
import nks.abc.domain.user.factory.UserFactory;
import nks.abc.domain.user.impl.AccountImpl;
import nks.abc.domain.user.impl.AdministratorImpl;
import nks.abc.domain.user.impl.StaffImpl;
import nks.abc.domain.user.impl.TeacherImpl;
import nks.abc.domain.user.impl.UserImpl;

@Service("staffService")
@Transactional(readOnly=true)
public class StaffServiceImpl implements StaffService {
	
	private static final Logger log = Logger.getLogger(StaffServiceImpl.class);
	
	@Autowired
	private AdminRepository adminDAO;
	@Autowired
	private TeacherRepository teacherDAO;
	@Autowired
	private AccountRepository accountDAO;
	@Autowired
	private MailService mailer;
	@Autowired
	private MailFactory mailFactory;
	
	private GuardClauses guardClauses = new GuardClauses();
	
	private ErrorHandler errorHandler;
	
	@Autowired
	@Qualifier("serviceErrorHandler")
	public void setErrorHandler(ErrorHandler errorHandler){
		this.errorHandler = errorHandler;
		this.errorHandler.loggerFor(this.getClass());
	}
	
	@Override
	@Transactional(readOnly=false)
	public void add(Account account, Teacher teacher, Administrator admin) {
		guardClauses.add(account, teacher, admin);

		try {
			String password = account.updatePasswordToRandom();
			log.info("adding account: " + account);
			accountDAO.insert(account);
		
			if (account.getIsAdministrator()) {
				admin.setAccountInfo(account);
				
				log.info("adding admin: " + admin);
				adminDAO.insert(admin);
			}
			if(account.getIsTeacher()) {
				teacher.setAccountInfo(account);
				
				log.info("adding teacher: " + teacher);
				teacherDAO.insert(teacher);
			}
			try{
				mailer.sendEmail(mailFactory.newStaff(account.getEmail(), password));
			}
			catch (SendMailException e){
				log.warn("Email with password wasn't sent to user " + account.getEmail());
			}
		} catch (Exception e) {
			errorHandler.handle(e, "add staff: " + account);
		}
		
	}

	@Override
	@Transactional(readOnly=false)
	public void update(Account account, Teacher teacher, Administrator admin, String currentUserLogin) {
		guardClauses.update(account, teacher, admin, currentUserLogin);
		
		accountDAO.update(account);
		
		//TODO: what be if will be changed relations between teacher/admin and account during update above???
		Teacher oldTeacher = teacherDAO.uniqueQuery(teacherDAO.specifications().byAccount(account));
		Administrator oldAdmin = adminDAO.uniqueQuery(adminDAO.specifications().byAccount(account));
		try{
			//TODO: make  more readable
			if(oldTeacher == null && account.getIsTeacher()) {
				oldTeacher = UserFactory.createTeacher();
			}
			if (oldTeacher != null){
				oldTeacher.setAccountInfo(account);
				log.info("updating teacher: " + account);
				teacherDAO.update(oldTeacher);
			}
			
			if(oldAdmin == null && account.getIsAdministrator()) {
				oldAdmin = UserFactory.createAdministrator();
			}
			if(oldAdmin != null){
				oldAdmin.setAccountInfo(account);
				log.info("updating administrator: " + oldAdmin);
				adminDAO.update(oldAdmin);
			}
		}
		catch (Exception e) {
			errorHandler.handle(e, "update staff: " + account);
		}
	}

	@Override
	@Transactional(readOnly=false)
	public void delete(Long id, String currentUserEmail) {
		guardClauses.delete(id, currentUserEmail);
		try{
			//TODO:related entities
			Account deletedUser = getAccountById(id);
			accountDAO.delete(deletedUser);
		}
		catch (Exception e){
			errorHandler.handle(e, "delete user id=" + id);
		}
	}
	
	@Override
	@Transactional(readOnly=false)
	public void fire(Long id, String currentUserEmail) {
		guardClauses.delete(id, currentUserEmail);
		try{
			Account disabledUser = getAccountById(id);
			disabledUser.setIsActive(false);
			accountDAO.update(disabledUser);
			log.info("disable user: " + disabledUser);
		}
		catch (Exception e){
			errorHandler.handle(e, "disable staff id=" + id); 
		}
	}

	@Override
	@Transactional(readOnly=false)
	public void rehire(Long id) {
		try{
			Account activatedUser = getAccountById(id);
			activatedUser.setIsActive(true);
			accountDAO.update(activatedUser);
			log.info("enable user: " + activatedUser);
		}
		catch (Exception e){
			errorHandler.handle(e, "disable staff id=" + id); 
		}
	};
	
	

	@Override
	public Account getAccountById(Long id) {
		Account account = null;
		try{
			account = accountDAO.uniqueQuery(AccountSpecifications.byId(id));
		}
		catch(Exception e){
			errorHandler.handle(e);
		}
		return account;
	}
	
	
	@Override
	public List<Account> getAllStaffAccounts() {
		List<User> users= new ArrayList<User>();
		Set<Account>accounts = new HashSet<Account>();
		try{
			//TODO: get staff via staff repository
			users.addAll(teacherDAO.retrieveAll());
			users.addAll(adminDAO.retrieveAll());
			for(User user : users) {
				accounts.add(user.getAccountInfo());
			}
		}
		catch (Exception e){
			errorHandler.handle(e, "get all staff");
		}
		return new ArrayList<Account>(accounts);
	}
	
	@Override
	public List<StaffImpl> getAllTeachers() {
//		List<Teacher> teachers = null;
//		try{
//			teachers = teacherDAO.query(AccountSpecifications.active(true));
//		}
//		catch(Exception e){
//			errorHandler.handle(e, "get all teachers");
//		}
//		List<StaffView> dtos = new ArrayList<StaffView>();
//		for(Teacher teacher: teachers){
//			dtos.add(dtoConvertor.toView(teacher.getAccountInfo()));
//		}
//		return dtos;
		return null;
	}

	@Override
	public Staff getStaffByEmail(String email) {
//		Account entity = null;
//		try{
//			entity = accountDAO.uniqueQuery(AccountSpecifications.byEmail(email).and(AccountSpecifications.active()));
//			
//		}
//		catch (Exception e){
//			errorHandler.handle(e, "get staff by email");
//		}
//		return dtoConvertor.toView(entity);
		return new StaffImpl();
	}
	
	
	private class GuardClauses{

		private void delete(Long id, String currentUserEmail) {
			Account currentUser = accountDAO.uniqueQuery(accountDAO.specifications().byEmailAndIsActive(currentUserEmail, true));
			
			if(currentUser == null) {
				throw new NoCurrentUserException("No current user. Username: " + currentUserEmail);
			}
			if(currentUser.getAccountId().equals(id)){
				throw new RightsDeprivingException("You're trying to remove yourself. Nice try :)");
			}
		}

		private void update(Account account, Teacher teacher, Administrator admin, String currentUserEmail) {
			//TODO: check teacher and administrator
			if(currentUserEmail == null || currentUserEmail.length() < 3) {
				throw new NoCurrentUserException("Current user login is empty");
			}
			System.out.println("Current user email: " + currentUserEmail);
			CriterionSpecification specification = accountDAO.specifications().byEmailAndIsActive(currentUserEmail,true);
			Account currentUser = accountDAO.uniqueQuery(specification);
			System.out.println("Current user: " + currentUser);
			Long accountId = account.getAccountId();
			if(accountId == null){
				throw new NoIdException("Trying to update account without id");
			}
			
			if(currentUser == null){
				throw new NoCurrentUserException();
			}
			// restrict removing admin rights for yourself
			// expects that change user can only administrator!!!
			if(accountId.equals(currentUser.getAccountId()) && !account.getIsAdministrator()){
				throw new RightsDeprivingException("You can't deprive administrator rights yourself");
			}
		}

		private void add(Account account, Teacher teacher, Administrator admin) {
			//TODO: check what of this are checked by domain annotation
			String login = account.getEmail();
			if(login == null || login.length() < 1) {
				throw new NoUserLoginException("Email is empty");
			}
			AccountSpecificationFactory specificationFactory = accountDAO.specifications();
			if(accountDAO.uniqueQuery(specificationFactory.byEmail(login)) != null) {
				throw new ServiceDisplayedErorr("User with such login already exist!");
			}
			if(!account.getIsAdministrator() && !account.getIsTeacher()){
				throw new ServiceDisplayedErorr("The Employee can't be neither a teacher nor an administrator");
			}
		}
	}


}
