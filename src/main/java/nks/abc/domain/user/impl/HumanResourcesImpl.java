package nks.abc.domain.user.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import nks.abc.core.exception.handler.ErrorHandler;
import nks.abc.core.exception.service.SendMailException;
import nks.abc.dao.repository.user.AccountRepository;
import nks.abc.dao.repository.user.AdminRepository;
import nks.abc.dao.repository.user.StaffRepository;
import nks.abc.dao.repository.user.TeacherRepository;
import nks.abc.dao.repository.user.UserRepositoty;
import nks.abc.dao.specification.factory.user.AccountSpecificationFactory;
import nks.abc.dao.specification.factory.user.UserSpecificationFactory;
import nks.abc.domain.errors.ErrorsSet;
import nks.abc.domain.message.MailFactory;
import nks.abc.domain.message.MailService;
import nks.abc.domain.user.Account;
import nks.abc.domain.user.Administrator;
import nks.abc.domain.user.HumanResources;
import nks.abc.domain.user.Staff;
import nks.abc.domain.user.Teacher;
import nks.abc.domain.user.User;

@Component
@Transactional(readOnly=true)
public class HumanResourcesImpl implements HumanResources {
	
	private static final Logger log = Logger.getLogger(HumanResourcesImpl.class);
	
	@Autowired
	private AdminRepository adminRepository;
	@Autowired
	private TeacherRepository teacherRepository;
	@Autowired
	private AccountRepository accountRepository;
	@Autowired
	private StaffRepository staffRepository;
	@Autowired
	private UserRepositoty userRepository;
	@Autowired
	private MailService mailer;
	@Autowired
	private MailFactory mailFactory;
	
	private Guard guard = new Guard();
	
	private ErrorHandler errorHandler;
	
	@Autowired
	@Qualifier("serviceErrorHandler")
	public void setErrorHandler(ErrorHandler errorHandler){
		this.errorHandler = errorHandler;
		this.errorHandler.loggerFor(this.getClass());
	}
	
	@Override
	@Transactional(readOnly=false)
	public void hireTeacher(Teacher teacher) {
		try {
			if(teacher == null || teacher.getAccount() == null){
				throw new IllegalArgumentException();
			}
			Account account = teacher.getAccount();
			String password = account.updatePasswordToRandom();
			log.info("adding teacher: " + teacher);
			teacherRepository.insert(teacher);
			sendWelcomeEmail(account, password);
		} catch (Exception e) {
			errorHandler.handle(e, "add staff: " + teacher);
		}
		
	}

	@Override
	@Transactional(readOnly=false)
	public void hireAdministrator(Administrator admin) {
		try {
			if(admin == null || admin.getAccount() == null){
				throw new IllegalArgumentException();
			}
			Account account = admin.getAccount();
			String password = account.updatePasswordToRandom();
			log.info("adding administrator: " + admin);
			adminRepository.insert(admin);
			sendWelcomeEmail(account, password);
		} catch (Exception e) {
			errorHandler.handle(e, "add staff: " + admin);
		}
			
	}

	public void sendWelcomeEmail(Account account, String password) {
		try{
			mailer.sendEmail(mailFactory.newStaff(account.getEmail(), password));
		}
		catch (SendMailException e){
			log.warn("Email with password wasn't sent to user " + account.getEmail());
		}
	}
	
	

	@Override
	public ErrorsSet<Staff> checkPosibilityOfUpdate(Staff staff, String currentUsername) {
		return guard.updateCheck(staff, currentUsername);
	}

	@Override
	@Transactional(readOnly=false)
	public void updateStaff(Staff staff, String currentUserEmail) {
		ErrorsSet<Staff> errors = guard.updateCheck(staff, currentUserEmail);
		if(errors.hasErrors()){
			//TODO: make specific exception
			log.warn("Got user error:" + errors.toString());
			throw new RuntimeException("User error:" + errors.toString());
		}
		try{
			System.out.println("Update staff:" + staff);
			userRepository.update(staff);
		}
		catch (Exception e) {
			errorHandler.handle(e, "update staff: " + staff);
		}
	}

	private Staff getStaffByAccountId(Long accountId) {
		return staffRepository.uniqueQuery(AccountSpecificationFactory.buildFactory().byId(accountId));
	}

	@Override
	public ErrorsSet<Staff> checkDeletePosibility(Long accountId, String currentUserEmail) {
		return guard.deleteCheck(accountId, currentUserEmail);
	}


	@Override
	@Transactional(readOnly=false)
	public void delete(Long accountId, String currentUserEmail) {
		try{
			ErrorsSet<Staff> errors = guard.deleteCheck(accountId, currentUserEmail);
			if(errors.hasErrors()){
				//TODO: make specific exception
				log.warn("Got user error:" + errors.toString());
				throw new RuntimeException("User error:" +errors.toString());
			}
			log.info("Deleting user:" + getStaffByAccountId(accountId));
			userRepository.delete(getStaffByAccountId(accountId));
		}
		catch (Exception e){
			errorHandler.handle(e, "delete user id=" + accountId);
		}
	}
	
	@Override
	public ErrorsSet<Staff> checkFirePosibility(Long accountId, String currentUserEmail) {
		return guard.deleteCheck(accountId, currentUserEmail);
	}
	
	@Override
	@Transactional(readOnly=false)
	public void fire(Long id, String currentUserEmail) {
		guard.deleteCheck(id, currentUserEmail);
		try{
			Account disabledUser = getAccountById(id);
			disabledUser.setIsActive(false);
			accountRepository.update(disabledUser);
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
			accountRepository.update(activatedUser);
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
			account = accountRepository.uniqueQuery(AccountSpecificationFactory.buildFactory().byId(id));
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
			users.addAll(teacherRepository.retrieveAll());
			users.addAll(adminRepository.retrieveAll());
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
			teachers = teacherRepository.query(AccountSpecificationFactory.buildFactory().active());
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
			entity = (Staff) userRepository.uniqueQuery(UserSpecificationFactory.buildFactory().byId(id));
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
		return new TeacherImpl();
	}
	
	
	private class Guard{

		private ErrorsSet<Staff> deleteCheck(Long accountId, String currentUserEmail) {
			try{
				AccountSpecificationFactory specification = AccountSpecificationFactory.buildFactory();
				Account currentUser = accountRepository.uniqueQuery(specification.byEmail(currentUserEmail).and(specification.active()));
				Staff staff = getStaffByAccountId(accountId);
				ErrorsSet<Staff> errors = new ErrorsSet<Staff>(staff);
				
				if(staff == null){
					throw new IllegalArgumentException("Employee not found. Account id: " + accountId);
				}
				if(currentUser == null) {
					throw new IllegalArgumentException("Current user not found. Username: " + currentUserEmail);
				}
				
				if(currentUser.getAccountId().equals(accountId)){
					errors.addError("You can't fire yourself");
				}
				if(staff.isTeacher()) {
					Teacher teacher = (Teacher) staff;
					if(teacher.countOfGroup() > 0){
						errors.addError("You can't fire teacher which have at least one group");
					}
				}
				return errors;
			}
			catch (Exception e) {
				errorHandler.handle(e, "checking delete posibility: Deleted id:" + accountId + ";Current username:" + currentUserEmail);
				throw new RuntimeException();
			}
		}

		private ErrorsSet<Staff> updateCheck(Staff staff, String currentUsername) {
			if(currentUsername == null) {
				throw new IllegalArgumentException("Current username is null");
			}
			if(staff == null){
				throw new IllegalArgumentException("Updating staff entity is null");
			}
			AccountSpecificationFactory accountSpecification = AccountSpecificationFactory.buildFactory();
			Account currentUser = accountRepository.uniqueQuery(accountSpecification.byEmail(currentUsername).and(accountSpecification.active()));
			if(currentUser == null){
				throw new IllegalArgumentException("Can't find curent user by email:" + currentUsername);
			}
			
			if(staff.getAccountId() == null || staff.getUserId() == null) {
				throw new IllegalArgumentException("Staff identifire is null");
			}
			ErrorsSet<Staff> errors = new ErrorsSet<Staff>(staff);
			// restrict removing admin rights for yourself
			// expects that change user can only administrator!!!
			//TODO: should forbid to deprive rights to staff module by myself
			if(staff.getAccountId().equals(currentUser.getAccountId()) && false /*!staff.isAdministrator()*/) {
				errors.addError("You can't deprive rightes by yourself");
			}
			return errors;
		}
	}

}
