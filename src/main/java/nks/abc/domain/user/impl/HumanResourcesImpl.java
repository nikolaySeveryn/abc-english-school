package nks.abc.domain.user.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import nks.abc.core.exception.handler.ErrorHandler;
import nks.abc.core.exception.service.NoCurrentUserException;
import nks.abc.core.exception.service.NoIdException;
import nks.abc.core.exception.service.NoUserLoginException;
import nks.abc.core.exception.service.RightsDeprivingException;
import nks.abc.core.exception.service.SendMailException;
import nks.abc.core.exception.service.ServiceDisplayedErorr;
import nks.abc.dao.repository.user.AccountRepository;
import nks.abc.dao.repository.user.AdminRepository;
import nks.abc.dao.repository.user.TeacherRepository;
import nks.abc.dao.repository.user.UserRepositoty;
import nks.abc.dao.specification.chunks.Specification;
import nks.abc.dao.specification.factory.user.AccountSpecificationFactory;
import nks.abc.dao.specification.factory.user.UserSpecificationFactory;
import nks.abc.domain.message.MailFactory;
import nks.abc.domain.message.MailService;
import nks.abc.domain.user.Account;
import nks.abc.domain.user.Administrator;
import nks.abc.domain.user.HumanResources;
import nks.abc.domain.user.Staff;
import nks.abc.domain.user.Teacher;
import nks.abc.domain.user.User;
import nks.abc.domain.user.factory.UserFactory;

@Service("staffService")
@Transactional(readOnly=true)
public class HumanResourcesImpl implements HumanResources {
	
	private static final Logger log = Logger.getLogger(HumanResourcesImpl.class);
	
	@Autowired
	private AdminRepository adminDAO;
	@Autowired
	private TeacherRepository teacherDAO;
	@Autowired
	private AccountRepository accountDAO;
	@Autowired
	private UserRepositoty userDAO;
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
				admin.setAccount(account);
				
				log.info("adding admin: " + admin);
				adminDAO.insert(admin);
			}
			if(account.getIsTeacher()) {
				teacher.setAccount(account);
				
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
//		Teacher oldTeacher = teacherDAO.uniqueQuery(teacherDAO.specifications().byAccount(account));
//		Administrator oldAdmin = adminDAO.uniqueQuery(adminDAO.specifications().byAccount(account));
		
		UserSpecificationFactory factory = UserSpecificationFactory.buildFactory();
		Teacher oldTeacher = teacherDAO.uniqueQuery(factory.byAccount(account));
		Administrator oldAdmin = adminDAO.uniqueQuery(factory.byAccount(account));
		
		try{
			//TODO: make  more readable
			if(oldTeacher == null && account.getIsTeacher()) {
				oldTeacher = UserFactory.createTeacher();
			}
			if (oldTeacher != null){
				oldTeacher.setAccount(account);
				log.info("updating teacher: " + account);
				teacherDAO.update(oldTeacher);
			}
			
			if(oldAdmin == null && account.getIsAdministrator()) {
				oldAdmin = UserFactory.createAdministrator();
			}
			if(oldAdmin != null){
				oldAdmin.setAccount(account);
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
			account = accountDAO.uniqueQuery(AccountSpecificationFactory.buildFactory().byId(id));
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
				accounts.add(user.getAccount());
			}
		}
		catch (Exception e){
			errorHandler.handle(e, "get all staff");
		}
		return new ArrayList<Account>(accounts);
	}
	
	@Override
	public List<Teacher> getAllTeachers() {
		List<Teacher> teachers = null;
		try{
			teachers = teacherDAO.query(AccountSpecificationFactory.buildFactory().active());
		}
		catch(Exception e){
			errorHandler.handle(e, "get all teachers");
		}
		return teachers;
	}

	@Override
	public Staff getStaffById(Long id) {
		Staff  entity = null;
		try{
			entity = (Staff) userDAO.uniqueQuery(UserSpecificationFactory.buildFactory().byId(id));
		}
		catch(Exception e){
			errorHandler.handle(e, "get staff by id");
		}
		return entity;
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
//			Account currentUser = accountDAO.uniqueQuery(accountDAO.specifications().byEmailAndIsActive(currentUserEmail, true));
			AccountSpecificationFactory specification = AccountSpecificationFactory.buildFactory();
			Account currentUser = accountDAO.uniqueQuery(specification.byEmail(currentUserEmail).and(specification.active()));
			
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
			
//			CriterionSpecification specification = accountDAO.specifications().byEmailAndIsActive(currentUserEmail,true);
			
			AccountSpecificationFactory specificationFactory = AccountSpecificationFactory.buildFactory();
			Specification specification = specificationFactory.byEmail(currentUserEmail).and(specificationFactory.active());
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
//			AccountSpecificationFactory specificationFactory = accountDAO.specifications();
			AccountSpecificationFactory specification = AccountSpecificationFactory.buildFactory();
			if(accountDAO.uniqueQuery(specification.byEmail(login)) != null) {
				throw new ServiceDisplayedErorr("User with such login already exist!");
			}
			if(!account.getIsAdministrator() && !account.getIsTeacher()){
				throw new ServiceDisplayedErorr("The Employee can't be neither a teacher nor an administrator");
			}
		}
	}




}
