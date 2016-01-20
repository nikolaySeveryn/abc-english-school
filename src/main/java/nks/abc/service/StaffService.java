package nks.abc.service;

import java.util.List;

import nks.abc.domain.dto.user.StaffDTO;
import nks.abc.domain.entity.user.User;


public interface StaffService {
	
	public void add(StaffDTO employee);
	public StaffDTO findById(Long id);
//	public List<StaffDTO> getAll(Integer offset, Integer limit);
	public List<StaffDTO> getAll();
	void delete(Long id, String currentUserLogin);
	void update(StaffDTO employeeDTO, String currentUserLogin);
	StaffDTO getStaffByLogin(String login);
}
