package nks.abc.bl.service.user;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import nks.abc.bl.domain.user.Account;
import nks.abc.bl.domain.user.Administrator;
import nks.abc.bl.domain.user.Teacher;
import nks.abc.bl.domain.user.User;
import nks.abc.bl.domain.user.UserFactory;
import nks.abc.bl.service.message.MailFactory;
import nks.abc.bl.service.message.MailService;
import nks.abc.bl.view.converter.user.AccountViewConverter;
import nks.abc.bl.view.object.objects.user.StaffView;
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
import nks.abc.dao.repository.user.AdministratorRepository;
import nks.abc.dao.repository.user.TeacherRepository;
import nks.abc.dao.specification.user.account.AccountSpecificationFactory;

@Service("staffService")
@Transactional(readOnly=true)
public class StaffServiceImpl implements StaffService {
	
	private static final Logger log = Logger.getLogger(StaffServiceImpl.class);
	
	@Autowired
	private AdministratorRepository adminDAO;
	@Autowired
	private TeacherRepository teacherDAO;
	@Autowired
	private AccountRepository accountDAO;
	@Autowired
	private AccountViewConverter dtoConvertor;
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
	public void add(StaffView employeeDTO) {
		guardClauses.add(employeeDTO);
		
		try {
			Account account = dtoConvertor.toDomain(employeeDTO);
			String password = account.updatePasswordToRandom();
			try{
				mailer.sendEmail(mailFactory.newStaff(account.getEmail(), password));
			}
			catch (SendMailException e){
				log.warn("Email with password wasn't sent to user " + account.getEmail());
			}
		
			if (employeeDTO.getIsAdministrator()) {
				Administrator admin = UserFactory.createAdministrator();
				admin.setAccountInfo(account);
				
				log.info("adding admin: " + admin);
				adminDAO.insert(admin);
			}
			if(employeeDTO.getIsTeacher()) {
				Teacher teacher = UserFactory.createTeacher();
				teacher.setAccountInfo(account);
				
				log.info("adding teacher: " + teacher);
				teacherDAO.insert(teacher);
			}
		} catch (Exception e) {
			errorHandler.handle(e, "add staff: " + employeeDTO);
		}
		
	}

	@Override
	@Transactional(readOnly=false)
	public void update(StaffView employeeDTO, String currentUserLogin) {
		guardClauses.update(employeeDTO, currentUserLogin);
		
		Account updatingUser = dtoConvertor.toDomain(employeeDTO);
		accountDAO.update(updatingUser);
		
		Teacher teacher = teacherDAO.uniqueQuery(teacherDAO.specifications().byAccount(updatingUser));
		Administrator admin = adminDAO.uniqueQuery(adminDAO.specifications().byAccount(updatingUser));
		try{
			if(teacher == null && updatingUser.isTeacher()) {
				teacher = UserFactory.createTeacher();
			}
			if (teacher != null){
				teacher.setAccountInfo(updatingUser);
				log.info("updating teacher: " + updatingUser);
				teacherDAO.update(teacher);
			}
			
			if(admin == null && updatingUser.isAdministrator()) {
				admin = UserFactory.createAdministrator();
			}
			if(admin != null){
				admin.setAccountInfo(updatingUser);
				log.info("updating administrator: " + admin);
				adminDAO.update(admin);
			}
		}
		catch (Exception e) {
			errorHandler.handle(e, "update staff: " + employeeDTO);
		}
	}

	@Override
	@Transactional(readOnly=false)
	public void delete(Long id, String currentUserEmail) {
		guardClauses.delete(id, currentUserEmail);
		try{
			//TODO:related entities
			Account deletedUser = getAccountInfoById(id);
			accountDAO.delete(deletedUser);
		}
		catch (Exception e){
			errorHandler.handle(e, "delete user id=" + id);
		}
	}
	
	@Override
	@Transactional(readOnly=false)
	public void disable(Long id, String currentUserEmail) {
		guardClauses.delete(id, currentUserEmail);
		try{
			Account disabledUser = getAccountInfoById(id);
			disabledUser.setIsDisable(true);
			accountDAO.update(disabledUser);
			log.info("disable user: " + disabledUser);
		}
		catch (Exception e){
			errorHandler.handle(e, "disable staff id=" + id); 
		}
	}

	@Override
	@Transactional(readOnly=false)
	public void enable(Long id) {
		try{
			Account activatedUser = getAccountInfoById(id);
			activatedUser.setIsDisable(false);
			accountDAO.update(activatedUser);
			log.info("enable user: " + activatedUser);
		}
		catch (Exception e){
			errorHandler.handle(e, "disable staff id=" + id); 
		}
	};
	
	

	@Override
	public StaffView getById(Long id) {
		try {
			Account account = getAccountInfoById(id);
			return dtoConvertor.toView(account);
		}
		catch(Exception e){
			errorHandler.handle(e, "get staff by id=" + id);
			return null;
		}
	}

	private Account getAccountInfoById(Long id) {
		return accountDAO.uniqueQuery(AccountSpecifications.byId(id));
	}

	@Override
	public List<StaffView> getAllStaff() {
		List<User> users= new ArrayList<User>();
		List<Account>accounts = new ArrayList<Account>();
		List<Long>accountIds = new ArrayList<Long>();
		try{
			users.addAll(teacherDAO.getAll());
			users.addAll(adminDAO.getAll());
			for(User user : users) {
				if(! accountIds.contains(user.getAccountId())) {
					accounts.add(user.getAccountInfo());
					accountIds.add(user.getAccountId());
				}
			}
		}
		catch (Exception e){
			errorHandler.handle(e, "get all staff");
		}
		
		return new ArrayList<StaffView>(dtoConvertor.toView(accounts));
	}
	
	@Override
	public List<StaffView> getAllTeachers() {
		List<Teacher> teachers = null;
		try{
			teachers = teacherDAO.query(AccountSpecifications.active(true));
		}
		catch(Exception e){
			errorHandler.handle(e, "get all teachers");
		}
		List<StaffView> dtos = new ArrayList<StaffView>();
		for(Teacher teacher: teachers){
			dtos.add(dtoConvertor.toView(teacher.getAccountInfo()));
		}
		return dtos;
	}

	@Override
	public StaffView getStaffByEmail(String email) {
		Account entity = null;
		try{
			entity = accountDAO.uniqueQuery(AccountSpecifications.byEmail(email).and(AccountSpecifications.active()));
			
		}
		catch (Exception e){
			errorHandler.handle(e, "get staff by email");
		}
		return dtoConvertor.toView(entity);
	}
	
	
	private class GuardClauses{

		private void delete(Long id, String currentUserEmail) {
			Account currentUser = accountDAO.uniqueQuery(accountDAO.specifications().byEmailAndDisable(currentUserEmail, false));
			
			if(currentUser == null) {
				throw new NoCurrentUserException("No current user. Username: " + currentUserEmail);
			}
			if(currentUser.getAccountId().equals(id)){
				throw new RightsDeprivingException("You're trying to remove yourself. Nice try :)");
			}
		}

		private void update(StaffView employeeDTO, String currentUserEmail) {
			if(currentUserEmail == null || currentUserEmail.length() < 1) {
				throw new NoCurrentUserException("Current user login is empty");
			}
			CriterionSpecification specification = accountDAO.specifications().byEmailAndDisable(currentUserEmail,false);
			Account currentUser = accountDAO.uniqueQuery(specification);
			if(employeeDTO.getAccountId() == null){
				throw new NoIdException("Trying to update account without id");
			}
			
			if(currentUser == null){
				throw new NoCurrentUserException();
			}
			// restrict removing admin rights for yourself
			// expects that change user can only administrator!!!
			if(employeeDTO.getAccountId().equals(currentUser.getAccountId()) && !employeeDTO.getIsAdministrator()){
				throw new RightsDeprivingException("You can't deprive administrator rights yourself");
			}
		}

		private void add(StaffView employeeDTO) {
			String login = employeeDTO.getEmail();
			if(login == null || login.length() < 1) {
				throw new NoUserLoginException("Email is empty");
			}
			AccountSpecificationFactory specificationFactory = accountDAO.specifications();
			if(accountDAO.uniqueQuery(specificationFactory.byEmail(login)) != null) {
				throw new ServiceDisplayedErorr("User with such login already exist!");
			}
			if(!employeeDTO.getIsAdministrator() && !employeeDTO.getIsTeacher()){
				throw new ServiceDisplayedErorr("Employee should be a teacher or an administrator or both of them");
			}
		}
	}
}
