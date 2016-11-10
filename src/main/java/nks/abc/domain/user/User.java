package nks.abc.domain.user;

public interface User {

	String getFullName();
	String getFullName(String separator);
	void updatePassword(String password);
	Boolean isNew();
	Long getUserId();
	void setUserId(Long id);
	Account getAccountInfo();
	void setAccountInfo(Account accountInfo);
	Long getAccountId();
	PersonalInfo getPersonalInfo();
}