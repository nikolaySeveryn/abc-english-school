package nks.abc.service;

import java.util.List;

import nks.abc.domain.entity.user.AccountInfo;
import nks.abc.domain.view.user.StaffView;


public interface StaffService {
	
	public void add(StaffView employee);
	public List<StaffView> getAll();
	public List<StaffView> getAllTeachers();
	void delete(Long id, String currentUserLogin);
	void update(StaffView employeeDTO, String currentUserLogin);
	StaffView getStaffByLogin(String login);
	StaffView getById(Long id);
}
