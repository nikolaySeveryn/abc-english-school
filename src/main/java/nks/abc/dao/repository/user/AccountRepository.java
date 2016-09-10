package nks.abc.dao.repository.user;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import nks.abc.bl.domain.user.Account;
import nks.abc.dao.base.BaseRepositoryImpl;
import nks.abc.dao.specification.user.account.AccountSpecificationFactory;

@Repository
public class AccountRepository extends BaseRepositoryImpl<Account> {
	
	private final static Logger log = Logger.getLogger(AccountRepository.class);
	
	@Autowired
	private AccountSpecificationFactory specificationFactory;

	public AccountRepository() {
		super(Account.class);
	}

	public AccountSpecificationFactory specifications(){
		return specificationFactory;
	}
}
