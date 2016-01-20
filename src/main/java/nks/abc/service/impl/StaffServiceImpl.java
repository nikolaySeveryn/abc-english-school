package nks.abc.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import nks.abc.dao.user.StaffDAO;
import nks.abc.dao.user.UserDAO;
import nks.abc.domain.dto.convertor.user.StaffDTOConvertor;
import nks.abc.domain.dto.user.StaffDTO;
import nks.abc.domain.entity.user.Staff;
import nks.abc.domain.entity.user.User;
import nks.abc.service.StaffService;

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
		if(login == null || login.length() < 1){
			throw new RuntimeException("Login is empty");
		}
		if(userDAO.findUserByLogin(login) != null){
			throw new RuntimeException("User with such login does exist");
		}
		employee.updatePassword(employeeDTO.getPassword());
		staffDAO.insert(employee);
	}

	@Override
	@Transactional(readOnly=false)
	public void update(StaffDTO employeeDTO, String currentUserLogin) {
		Staff updatingUser = convertFromDTO(employeeDTO);
		User currentUser = userDAO.findUserByLogin(currentUserLogin);
		// restrict removing admin rights for yourself
		if(updatingUser.getUserId().equals(currentUser.getUserId()) && !updatingUser.isAdministrator()){
			throw new RuntimeException("You can't deprive administrator powers yourself");
		}
		System.out.println("update :" + updatingUser);
		staffDAO.update(updatingUser);
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

	@Override
	@Transactional(readOnly=false)
	public void delete(Long id, String currentUserLogin) {
		User currentUser = staffDAO.findUserByLogin(currentUserLogin);
		if(currentUser == null) {
			throw new RuntimeException("No current user");
		}
		if(currentUser.getUserId().equals(id)){
			throw new RuntimeException("You're trying to remove yourself. Nice try :)");
		}
		staffDAO.deleteById(id);
	}

	@Override
	public StaffDTO findById(Long id) {
		return convertToDTO(staffDAO.findById(id));
	}

	@Override
	public List<StaffDTO> getAll() {
		List<StaffDTO> allStaff = new ArrayList<StaffDTO>();
		System.out.println("entity size: " + staffDAO.getAll().size());
		StaffDTOConvertor.toDTO(staffDAO.getAll(), allStaff);
		System.out.println("dto size: " + allStaff.size());
		return  allStaff;
	}

	@Override
	public StaffDTO getStaffByLogin(String login) {
		Staff entity = staffDAO.findUserByLogin(login);
		return convertToDTO(entity);
	}


}
