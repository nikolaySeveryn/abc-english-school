package nks.abc.domain.user;

public interface User {

	String getFullName();
	String getFullName(String separator);
	void updatePassword(String password);
	Boolean isNew();
	Long getUserId();
	void setUserId(Long id);
	Account getAccount();
	void setAccount(Account accountInfo);
	Long getAccountId();
	PersonalInfo getPersonalInfo();
	boolean isTeacher();
	boolean isAdministrator();
	boolean isStudent();
}