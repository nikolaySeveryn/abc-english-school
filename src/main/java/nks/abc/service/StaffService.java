package nks.abc.service;

import java.util.List;

import nks.abc.domain.dto.user.StaffDTO;


public interface StaffService {
	
	public void add(StaffDTO employee);
	public List<StaffDTO> getAll();
	void delete(Long id, String currentUserLogin);
	void update(StaffDTO employeeDTO, String currentUserLogin);
	StaffDTO getStaffByLogin(String login);
}
