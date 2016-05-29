package nks.abc.dao.repository.user;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import nks.abc.dao.base.BaseHibernrateRepositoryImpl;
import nks.abc.dao.specification.user.account.AccountInfoSpecificationFactory;
import nks.abc.domain.entity.user.AccountInfo;

@Repository
public class AccountRepository extends BaseHibernrateRepositoryImpl<AccountInfo> {
	
	private final static Logger log = Logger.getLogger(AccountRepository.class);

	public AccountRepository() {
		super(AccountInfo.class);
		System.out.println("Accout construct");
	}

	public AccountInfoSpecificationFactory getSpecificationFactory(){
		return new AccountInfoSpecificationFactory();
	}
}
