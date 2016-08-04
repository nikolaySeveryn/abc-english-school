package nks.abc.bl.domain.user.factory;

import nks.abc.bl.domain.user.Account;

public class AccountFactory {
	public Account createAccount(){
		Account account = new Account();
		account.setIsDisable(false);
		return account;
	}
}
