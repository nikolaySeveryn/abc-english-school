package nks.abc.bl.service;

import java.util.List;

import nks.abc.bl.domain.user.Account;
import nks.abc.bl.view.object.objects.user.StaffView;


public interface StaffService {
	
	public void add(StaffView employee);
	public List<StaffView> getAll();
	public List<StaffView> getAllTeachers();
	void delete(Long id, String currentUserLogin);
	void update(StaffView employeeDTO, String currentUserLogin);
	StaffView getStaffByLogin(String login);
	StaffView getById(Long id);
}
