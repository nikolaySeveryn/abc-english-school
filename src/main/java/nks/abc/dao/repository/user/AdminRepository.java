package nks.abc.dao.repository.user;

import org.springframework.stereotype.Repository;

import nks.abc.dao.base.BaseRepositoryImpl;
import nks.abc.domain.user.Administrator;
import nks.abc.domain.user.impl.AdministratorImpl;

@Repository
public class AdminRepository extends BaseRepositoryImpl<Administrator> {

	
	public AdminRepository() {
		super(AdministratorImpl.class);
	}

}
