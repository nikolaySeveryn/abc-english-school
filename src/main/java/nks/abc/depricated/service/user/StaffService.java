package nks.abc.depricated.service.user;

import java.util.List;

import nks.abc.depricated.view.object.objects.user.StaffView;
import nks.abc.domain.user.Account;


public interface StaffService {
	
	public void add(StaffView employee);
	public List<StaffView> getAllStaff();
	public List<StaffView> getAllTeachers();
	void delete(Long id, String currentUserEmail);
	void disable(Long id, String currentUserEmail);
	void enable(Long id);
	void update(StaffView employeeDTO, String currentUserEmail);
	StaffView getStaffByEmail(String login);
	StaffView getById(Long id);
}
