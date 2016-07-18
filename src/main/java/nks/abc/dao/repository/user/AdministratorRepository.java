package nks.abc.dao.repository.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import nks.abc.bl.domain.user.Administrator;
import nks.abc.dao.base.BaseRepositoryImpl;
import nks.abc.dao.specification.user.administrator.AdministratorSpecificationFactory;

@Repository
public class AdministratorRepository extends BaseRepositoryImpl<Administrator> {

	@Autowired
	private AdministratorSpecificationFactory specificationFactory;
	
	public AdministratorRepository() {
		super(Administrator.class);
	}

	public AdministratorSpecificationFactory specifications(){
		return specificationFactory;
	}
}
