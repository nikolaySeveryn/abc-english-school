package nks.abc.depricated.service.user;

import java.util.List;

import nks.abc.depricated.view.object.objects.user.StaffView;
import nks.abc.domain.user.Account;
import nks.abc.domain.user.Administrator;
import nks.abc.domain.user.Staff;
import nks.abc.domain.user.Teacher;
import nks.abc.domain.user.impl.AccountImpl;
import nks.abc.domain.user.impl.AdministratorImpl;
import nks.abc.domain.user.impl.StaffImpl;
import nks.abc.domain.user.impl.TeacherImpl;


public interface StaffService {
	
	public void add(Account account, Teacher teacher, Administrator admin);
	public List<Account> getAllStaffAccounts();
	public List<StaffImpl> getAllTeachers();
	void delete(Long id, String currentUserEmail);
	void fire(Long id, String currentUserEmail);
	void rehire(Long id);
	void update(Account account, Teacher teacher, Administrator admin, String currentUserEmail);
	Staff getStaffByEmail(String login);
	public Account getAccountById(Long id);
}
