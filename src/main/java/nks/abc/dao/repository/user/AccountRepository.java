package nks.abc.dao.repository.user;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import nks.abc.dao.base.BaseRepositoryImpl;
import nks.abc.domain.user.Account;
import nks.abc.domain.user.impl.AccountImpl;

@Repository
public class AccountRepository extends BaseRepositoryImpl<Account> {
	
	private final static Logger log = Logger.getLogger(AccountRepository.class);
	
	public AccountRepository() {
		super(AccountImpl.class);
	}

}
