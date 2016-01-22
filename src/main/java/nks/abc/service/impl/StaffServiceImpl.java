package nks.abc.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import nks.abc.dao.exception.DAOException;
import nks.abc.dao.user.StaffDAO;
import nks.abc.dao.user.UserDAO;
import nks.abc.domain.dto.convertor.user.StaffDTOConvertor;
import nks.abc.domain.dto.user.StaffDTO;
import nks.abc.domain.entity.user.Staff;
import nks.abc.domain.entity.user.User;
import nks.abc.service.StaffService;
import nks.abc.service.exception.RightsDeprivingException;
import nks.abc.service.exception.ServiceDisplayedErorr;
import nks.abc.service.exception.ServiceException;

@Service("staffService")
@Transactional(readOnly=true)
public class StaffServiceImpl implements StaffService {
	
	@Autowired
	private StaffDAO staffDAO;
	@Autowired
	private UserDAO userDAO;
	
	@Override
	@Transactional(readOnly=false)
	public void add(StaffDTO employeeDTO) {
		Staff employee = convertFromDTO(employeeDTO);
		String login = employee.getLogin();
		if(login == null || login.length() < 1) {
			throw new ServiceException("Login is empty");
		}
		if(userDAO.findUserByLogin(login) != null) {
			throw new ServiceDisplayedErorr("User with such login already exist!");
		}
		employee.updatePassword(employeeDTO.getPassword());
		try{
			staffDAO.insert(employee);
		}
		catch (DAOException de){
			throw new ServiceException("dao error", de);
		}
	}

	@Override
	@Transactional(readOnly=false)
	public void update(StaffDTO employeeDTO, String currentUserLogin) {
		Staff updatingUser = convertFromDTO(employeeDTO);
		User currentUser = userDAO.findUserByLogin(currentUserLogin);
		// restrict removing admin rights for yourself
		if(updatingUser.getUserId().equals(currentUser.getUserId()) && !updatingUser.isAdministrator()){
			throw new RightsDeprivingException("You can't deprive administrator rights yourself");
		}
		System.out.println("update :" + updatingUser);
		try{
			staffDAO.update(updatingUser);
		}
		catch (DAOException de){
			throw new ServiceException("dao error", de);
		}
	}

	@Override
	@Transactional(readOnly=false)
	public void delete(Long id, String currentUserLogin) {
		User currentUser = staffDAO.findUserByLogin(currentUserLogin);
		if(currentUser == null) {
			throw new ServiceException("No current user");
		}
		if(currentUser.getUserId().equals(id)){
			throw new RightsDeprivingException("You're trying to remove yourself. Nice try :)");
		}
		try{
			staffDAO.deleteById(id);
		}
		catch (DAOException de){
			throw new ServiceException("dao error", de);
		}
	}
	
	@Override
	public StaffDTO findById(Long id) {
		Staff staff = null;
		try {
			staff = staffDAO.findById(id);
		} 
		catch (DAOException de) {
			throw new ServiceException("dao error", de);
		}
		return convertToDTO(staff);
	}

	@Override
	public List<StaffDTO> getAll() {
		List<StaffDTO> staffDTOs = new ArrayList<StaffDTO>();
		List<Staff> staffEntities = null;
		try{
			staffEntities = staffDAO.getAll();
		}
		catch (DAOException de){
			throw new ServiceException("dao error", de);
		}
		StaffDTOConvertor.toDTO(staffEntities, staffDTOs);
		return  staffDTOs;
	}

	@Override
	public StaffDTO getStaffByLogin(String login) {
		Staff entity = null;
		try{
			entity = staffDAO.findUserByLogin(login);
		}
		catch (DAOException de){
			throw new ServiceException("dao error", de);
		}
		return convertToDTO(entity);
	}

	private Staff convertFromDTO(StaffDTO employeeDTO){
		Staff employee = new Staff();
		StaffDTOConvertor.toEntity(employeeDTO, employee);
		return employee;
	}
	
	private StaffDTO convertToDTO(Staff entity){
		StaffDTO employee = new StaffDTO();
		StaffDTOConvertor.toDTO(entity, employee);
		return employee;
	}


}
