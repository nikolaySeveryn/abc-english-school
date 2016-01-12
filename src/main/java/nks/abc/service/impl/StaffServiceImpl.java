package nks.abc.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;

import org.jboss.ejb3.context.spi.SessionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import nks.abc.dao.UserDAO;
import nks.abc.domain.dto.convertor.user.StaffDTOConvertor;
import nks.abc.domain.dto.user.StaffDTO;
import nks.abc.domain.entity.user.Staff;
import nks.abc.domain.entity.user.User;
import nks.abc.service.StaffService;

@Service("staffService")
@Transactional(readOnly=true)
public class StaffServiceImpl implements StaffService {
	
	@Autowired
	private UserDAO userDAO;

	@Override
	@Transactional(readOnly=false)
	public void insert(StaffDTO employeeDTO) {
		//TODO: unque login
		Staff employee = convertFromDTO(employeeDTO);
		String login = employee.getLogin();
		if(login == null || login.length() < 1){
			throw new RuntimeException("Login is empty");
		}
		if(userDAO.findUserByLogin(login) != null){
			throw new RuntimeException("User with such login does exist");
		}
		employee.updatePassword(employeeDTO.getPassword());
		userDAO.insert(employee);
	}

	@Override
	@Transactional(readOnly=false)
	public void update(StaffDTO employeeDTO) {
		Staff entity = convertFromDTO(employeeDTO);
		System.out.println("update entity: " + entity);
		if(entity.getUserId().equals(getCurrentUser().getUserId()) && !entity.isAdministrator()){
			throw new RuntimeException("You can't deprive administrator powers yourself");
		}
		System.out.println("update :" + entity);
		userDAO.update(entity);
	}
	
	private Staff convertFromDTO(StaffDTO employeeDTO){
		Staff employee = new Staff();
		StaffDTOConvertor.toEntity(employeeDTO, employee);
		return employee;
	}

	@Override
	@Transactional(readOnly=false)
	public void delete(Long id) {
		if(getCurrentUser().getUserId().equals(id)){
			throw new RuntimeException("You're trying to remove yourself. Nice try :)");
		}
		userDAO.deleteById(id);
	}

	protected User getCurrentUser() {
		String currentUserLogin = FacesContext.getCurrentInstance().getExternalContext().getRemoteUser();
		User currentUser = userDAO.findUserByLogin(currentUserLogin);
		if(currentUser == null){
			throw new RuntimeException("No current user");
		}
		return currentUser;
	}

	@Override
	public StaffDTO findById(Long id) {
		StaffDTO employee = new StaffDTO();
		StaffDTOConvertor.toDTO(userDAO.findStaffById(id), employee);
		return employee;
	}

	@Override
	public List<StaffDTO> getAll() {
		List<StaffDTO> allStaff = new ArrayList<StaffDTO>();
		System.out.println("entity size: " + userDAO.getAll().size());
		StaffDTOConvertor.toDTO(userDAO.getAllStaff(), allStaff);
		System.out.println("dto size: " + allStaff.size());
		return  allStaff;
	}


}
