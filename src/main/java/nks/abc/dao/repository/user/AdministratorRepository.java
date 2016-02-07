package nks.abc.dao.repository.user;

import org.springframework.stereotype.Repository;

import nks.abc.dao.base.BaseHibernrateRepositoryImpl;
import nks.abc.dao.specification.user.administrator.AdministratorSpecificationFactory;
import nks.abc.domain.entity.user.Administrator;

@Repository
public class AdministratorRepository extends BaseHibernrateRepositoryImpl<Administrator> {

	public AdministratorRepository() {
		super(Administrator.class);
	}

	public AdministratorSpecificationFactory getSpecificationFactory(){
		return new AdministratorSpecificationFactory();
	}
}
