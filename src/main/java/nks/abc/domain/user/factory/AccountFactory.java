package nks.abc.domain.user.factory;

import nks.abc.domain.user.Account;
import nks.abc.domain.user.PersonalInfo;
import nks.abc.domain.user.impl.AccountImpl;
import nks.abc.domain.user.impl.PersonalInfoImpl;

public class AccountFactory {
	
	public static Account createAccount(){
		Account instance = new AccountImpl();
		instance.setPeronalInfo(createPersonalInfo());
		instance.setIsActive(true);
		return instance;
	}
	
	public static PersonalInfo createPersonalInfo(){
		PersonalInfo instance = new PersonalInfoImpl();
		return instance;
	}
}
