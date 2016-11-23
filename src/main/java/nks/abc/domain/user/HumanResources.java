package nks.abc.domain.user;

import java.util.List;

import nks.abc.domain.errors.ErrorsSet;


public interface HumanResources {
	
	public void hireTeacher(Teacher teacher);
	public void hireAdministrator(Administrator admin);
	public List<Account> getAllStaffAccounts();
	public List<Teacher> getAllTeachers();
	public ErrorsSet<Staff> checkDeletePosibility(Long id, String currentUserEmail);
	public void deleteStaff(Long id, String currentUsername);
	public ErrorsSet<Staff> checkFirePosibility(Long id, String currentUserEmail);
	public void fire(Long id, String currentUserEmail);
	public void rehire(Long id);
	public ErrorsSet<Staff>checkPosibilityOfUpdate(Staff staff, String currentUsername);
	public void updateStaff(Staff staff, String currentUserEmail);
	public Staff getActiveStaffByEmail(String login);
	public Account getAccountById(Long id);
	public Staff getStaffById(Long id);
}
