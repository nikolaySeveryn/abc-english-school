package nks.abc.domain.user.factory;

import nks.abc.domain.user.Account;

public class AccountFactory {
	public Account createAccount(){
		Account account = new Account();
		account.setIsDisable(false);
		return account;
	}
}
