package nks.abc.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import nks.abc.dao.base.HibernateSpecification;
import nks.abc.dao.exception.DAOException;
import nks.abc.dao.repository.user.AccountRepository;
import nks.abc.dao.repository.user.AdministratorRepository;
import nks.abc.dao.repository.user.TeacherRepository;
import nks.abc.dao.specification.user.account.AccountInfoSpecificationFactory;
import nks.abc.domain.dto.convertor.user.StaffDTOConvertor;
import nks.abc.domain.dto.user.StaffDTO;
import nks.abc.domain.entity.user.Administrator;
import nks.abc.domain.entity.user.AccountInfo;
import nks.abc.domain.entity.user.Teacher;
import nks.abc.domain.entity.user.UserFactory;
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
	
	@Override
	@Transactional(readOnly=false)
	public void add(StaffDTO employeeDTO) {
		
		String login = employeeDTO.getLogin();
		if(login == null || login.length() < 1) {
			throw new NoUserLoginException("Login is empty");
		}
		AccountInfoSpecificationFactory specificationFactory = accountDAO.getSpecificationFactory();
		if(accountDAO.uniqueQuery(specificationFactory.byLogin(login)) != null) {
			throw new ServiceDisplayedErorr("User with such login already exist!");
		}
		if(!employeeDTO.getIsAdministrator() && !employeeDTO.getIsTeacher()){
			throw new ServiceDisplayedErorr("Employee should be a teacher or an administrator or both of them");
		}
		
		AccountInfo account = convertAccountFromDTO(employeeDTO);
		account.setIsDeleted(false);
		account.updatePassword(employeeDTO.getPassword());
		
		try {
			if (employeeDTO.getIsAdministrator()) {
				Administrator admin = UserFactory.createAdministrator();
				admin.setAccountInfo(account);
				
				log.info("add admin: " + account);
				adminDAO.insert(admin);
			}
			if(employeeDTO.getIsTeacher()) {
				Teacher teacher = UserFactory.createTeacher();
				teacher.setAccountInfo(account);
				
				log.info("add teacher");
				teacherDAO.insert(teacher);
			}
		} catch (DAOException de) {
			log.error("add staff: DAO error", de);
			throw new ServiceException("dao error", de);
		}
		
	}

	@Override
	@Transactional(readOnly=false)
	public void update(StaffDTO employeeDTO, String currentUserLogin) {
		updateGuardClause(employeeDTO, currentUserLogin);
		
		AccountInfo updatingUser = convertAccountFromDTO(employeeDTO);
		
		Teacher teacher = teacherDAO.uniqueQuery(teacherDAO.getSpecificaitonFactory().byAccount(updatingUser));
		Administrator admin = adminDAO.uniqueQuery(adminDAO.getSpecificationFactory().byAccount(updatingUser));
		try{
			if(teacher == null && updatingUser.isTeacher()) {
				teacher = UserFactory.createTeacher();
			}
			if (teacher != null){
				teacher.setAccountInfo(updatingUser);
				log.info("update teacher: " + updatingUser);
				teacherDAO.update(teacher);
			}
			
			if(admin == null && updatingUser.isAdministrator()) {
				admin = UserFactory.createAdministrator();
			}
			if(admin != null){
				admin.setAccountInfo(updatingUser);
				log.info("update administrator: " + admin);
				adminDAO.update(admin);
			}
		}
		catch (DAOException de) {
			log.error("DAO exception on update staff", de);
			throw new ServiceException("dao error", de);
		}
	}

	private void updateGuardClause(StaffDTO employeeDTO,
			String currentUserLogin) {
		if(currentUserLogin == null || currentUserLogin.length() < 1) {
			throw new NoCurrentUserException("Current user login is empty");
		}
		HibernateSpecification specification = accountDAO.getSpecificationFactory().byLoginAndDeleted(currentUserLogin,false);
		AccountInfo currentUser = accountDAO.uniqueQuery(specification);
		if(employeeDTO.getId() == null){
			throw new NoIdException("Trying to update account without id");
		}
		
		if(currentUser == null){
			throw new NoCurrentUserException();
		}
//		// restrict removing admin rights for yourself
		// expects that change user can only administrator!!!
		if(employeeDTO.getId().equals(currentUser.getAccountId()) && !employeeDTO.getIsAdministrator()){
			throw new RightsDeprivingException("You can't deprive administrator rights yourself");
		}
	}

	@Override
	@Transactional(readOnly=false)
	public void delete(Long id, String currentUserLogin) {
		deleteGuardClause(id, currentUserLogin);
		try{
			log.info("delete user: ");
			AccountInfo removed = accountDAO.uniqueQuery(accountDAO.getSpecificationFactory().byId(id));
			removed.setIsDeleted(true);
			accountDAO.update(removed);
		}
		catch (DAOException de){
			log.error("DAO exception on staff deleting", de);
			throw new ServiceException("dao error", de);
		}
	}

	private void deleteGuardClause(Long id, String currentUserLogin) {
		AccountInfo currentUser = accountDAO.uniqueQuery(accountDAO.getSpecificationFactory().byLoginAndDeleted(currentUserLogin, false));
		if(currentUser == null) {
			throw new NoCurrentUserException("No current user. Username: " + currentUserLogin);
		}
		if(currentUser.getAccountId().equals(id)){
			throw new RightsDeprivingException("You're trying to remove yourself. Nice try :)");
		}
	}
	

	@Override
	public List<StaffDTO> getAll() {
		List<AccountInfo> accounts= null;
		List<StaffDTO> dtos = new ArrayList<StaffDTO>();
		try{
			accounts = accountDAO.query(accountDAO.getSpecificationFactory().byIsDeleted(false));
		}
		catch (DAOException de){
			log.error("DAO exception on staff finding all", de);
			throw new ServiceException("dao error", de);
		}
		StaffDTOConvertor.toDTO(accounts, dtos);
		return  dtos;
	}

	@Override
	public StaffDTO getStaffByLogin(String login) {
		AccountInfo entity = null;
		try{
			entity = accountDAO.uniqueQuery(accountDAO.getSpecificationFactory().byLoginAndDeleted(login, false));
			
		}
		catch (DAOException de){
			throw new ServiceException("dao error", de);
		}
		return convertAccountToDTO(entity);
	}

	private AccountInfo convertAccountFromDTO(StaffDTO employeeDTO){
		AccountInfo employee = new AccountInfo();
		StaffDTOConvertor.toEntity(employeeDTO, employee);
		return employee;
	}
	
	private StaffDTO convertAccountToDTO(AccountInfo entity){
		StaffDTO employee = new StaffDTO();
		StaffDTOConvertor.toDTO(entity, employee);
		return employee;
	}


}
