package nks.abc.bl.service.user;

import java.util.List;

import nks.abc.bl.domain.user.Account;
import nks.abc.bl.view.object.objects.user.StaffView;


public interface StaffService {
	
	public void add(StaffView employee);
	public List<StaffView> getAll();
	public List<StaffView> getAllTeachers();
	void delete(Long id, String currentUserEmail);
	void disable(Long id, String currentUserEmail);
	void enable(Long id);
	void update(StaffView employeeDTO, String currentUserEmail);
	StaffView getStaffByEmail(String login);
	StaffView getById(Long id);
}
