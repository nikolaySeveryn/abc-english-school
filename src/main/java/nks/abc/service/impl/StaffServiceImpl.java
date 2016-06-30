package nks.abc.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import nks.abc.dao.base.interfaces.CriterionSpecification;
import nks.abc.dao.exception.DAOException;
import nks.abc.dao.repository.user.AccountRepository;
import nks.abc.dao.repository.user.AdministratorRepository;
import nks.abc.dao.repository.user.TeacherRepository;
import nks.abc.dao.specification.user.account.AccountInfoSpecificationFactory;
import nks.abc.domain.entity.user.Administrator;
import nks.abc.domain.entity.user.Account;
import nks.abc.domain.entity.user.Teacher;
import nks.abc.domain.entity.user.UserFactory;
import nks.abc.domain.view.converter.user.AccountViewConverter;
import nks.abc.domain.view.object.objects.user.StaffView;
import nks.abc.service.StaffService;
import nks.abc.service.exception.NoCurrentUserException;
import nks.abc.service.exception.NoUserLoginException;
import nks.abc.service.exception.NoIdException;
import nks.abc.service.exception.RightsDeprivingException;
import nks.abc.service.exception.ServiceDisplayedErorr;
import nks.abc.service.exception.ServiceException;

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
	
	private GuardClauses guardClauses = new GuardClauses();
	
	@Override
	@Transactional(readOnly=false)
	public void add(StaffView employeeDTO) {
		guardClauses.add(this, employeeDTO);
		
		Account account = dtoConvertor.toDomain(employeeDTO);
		account.updatePassword(employeeDTO.getPassword());
		
		try {
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
		} catch (DAOException de) {
			log.error("add staff: DAO error", de);
			throw new ServiceException("dao error", de);
		}
		
	}

	@Override
	@Transactional(readOnly=false)
	public void update(StaffView employeeDTO, String currentUserLogin) {
		guardClauses.update(this, employeeDTO, currentUserLogin);
		
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
		catch (DAOException de) {
			log.error("DAO exception on update staff", de);
			throw new ServiceException("dao error", de);
		}
	}

	@Override
	@Transactional(readOnly=false)
	public void delete(Long id, String currentUserLogin) {
		guardClauses.delete(this, id, currentUserLogin);
		
		try{
			log.info("delete user: ");
			Account removed = getAccountInfoById(id);
			removed.setIsDeleted(true);
			accountDAO.update(removed);
		}
		catch (DAOException de){
			log.error("DAO exception on staff deleting", de);
			throw new ServiceException("dao error", de);
		}
	}
	
	

	@Override
	public StaffView getById(Long id) {
		try {
			Account account = getAccountInfoById(id);
			return dtoConvertor.toView(account);
		}
		catch(DAOException de){
			log.error("DAO exception on geting by id", de);
			throw new ServiceException("dao error", de);
		}
	}

	private Account getAccountInfoById(Long id) {
		return accountDAO.uniqueQuery(accountDAO.specifications().byId(id));
	}

	@Override
	public List<StaffView> getAll() {
		List<Account> accounts= null;
		try{
			accounts = accountDAO.query(accountDAO.specifications().byIsDeleted(false));
		}
		catch (DAOException de){
			log.error("DAO exception on staff finding all", de);
			throw new ServiceException("dao error", de);
		}
		
		return new ArrayList<StaffView>(dtoConvertor.toView(accounts));
	}
	
	@Override
	public List<StaffView> getAllTeachers() {
		List<Teacher> teachers = null;
		try{
			teachers = teacherDAO.query(teacherDAO.specifications().byIsDeleted(false));
		}
		catch(DAOException de){
			log.error("DAO exception on teacher finding", de);
			throw new ServiceException("dao error", de);
		}
		List<StaffView> dtos = new ArrayList<StaffView>();
		for(Teacher teacher: teachers){
			dtos.add(dtoConvertor.toView(teacher.getAccountInfo()));
		}
		return dtos;
	}

	@Override
	public StaffView getStaffByLogin(String login) {
		Account entity = null;
		try{
			entity = accountDAO.uniqueQuery(accountDAO.specifications().byLoginAndDeleted(login, false));
			
		}
		catch (DAOException de){
			throw new ServiceException("dao error", de);
		}
		return dtoConvertor.toView(entity);
	}
	
	private static class GuardClauses{

		private void delete(StaffServiceImpl staffServiceImpl, Long id, String currentUserLogin) {
			Account currentUser = staffServiceImpl.accountDAO.uniqueQuery(staffServiceImpl.accountDAO.specifications().byLoginAndDeleted(currentUserLogin, false));
			if(currentUser == null) {
				throw new NoCurrentUserException("No current user. Username: " + currentUserLogin);
			}
			if(currentUser.getAccountId().equals(id)){
				throw new RightsDeprivingException("You're trying to remove yourself. Nice try :)");
			}
		}

		private void update(StaffServiceImpl staffServiceImpl, StaffView employeeDTO, String currentUserLogin) {
			if(currentUserLogin == null || currentUserLogin.length() < 1) {
				throw new NoCurrentUserException("Current user login is empty");
			}
			CriterionSpecification specification = staffServiceImpl.accountDAO.specifications().byLoginAndDeleted(currentUserLogin,false);
			Account currentUser = staffServiceImpl.accountDAO.uniqueQuery(specification);
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

		private void add(StaffServiceImpl staffServiceImpl, StaffView employeeDTO) {
			String login = employeeDTO.getLogin();
			if(login == null || login.length() < 1) {
				throw new NoUserLoginException("Login is empty");
			}
			AccountInfoSpecificationFactory specificationFactory = staffServiceImpl.accountDAO.specifications();
			if(staffServiceImpl.accountDAO.uniqueQuery(specificationFactory.byLogin(login)) != null) {
				throw new ServiceDisplayedErorr("User with such login already exist!");
			}
			if(!employeeDTO.getIsAdministrator() && !employeeDTO.getIsTeacher()){
				throw new ServiceDisplayedErorr("Employee should be a teacher or an administrator or both of them");
			}
		}
	};
}
