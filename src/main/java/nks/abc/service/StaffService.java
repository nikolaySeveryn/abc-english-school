package nks.abc.service;

import java.util.List;

import nks.abc.domain.dto.user.StaffDTO;
import nks.abc.domain.entity.user.AccountInfo;


public interface StaffService {
	
	public void add(StaffDTO employee);
	public List<StaffDTO> getAll();
	public List<StaffDTO> getAllTeachers();
	void delete(Long id, String currentUserLogin);
	void update(StaffDTO employeeDTO, String currentUserLogin);
	StaffDTO getStaffByLogin(String login);
	StaffDTO getById(Long id);
}
