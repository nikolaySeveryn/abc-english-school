package nks.abc.domain.user;

public interface User {

	void updatePassword(String password);
	Boolean isNew();
	Long getUserId();
	void setUserId(Long id);
	Account getAccountInfo();
	void setAccountInfo(Account accountInfo);
	Long getAccountId();
}