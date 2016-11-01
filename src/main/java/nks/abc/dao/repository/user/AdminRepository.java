package nks.abc.dao.repository.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import nks.abc.dao.base.BaseRepositoryImpl;
import nks.abc.dao.specification.user.administrator.AdministratorSpecificationFactory;
import nks.abc.domain.user.Administrator;
import nks.abc.domain.user.impl.AdministratorImpl;

@Repository
public class AdminRepository extends BaseRepositoryImpl<Administrator> {

	@Autowired
	private AdministratorSpecificationFactory specificationFactory;
	
	public AdminRepository() {
		super(AdministratorImpl.class);
	}

	public AdministratorSpecificationFactory specifications(){
		return specificationFactory;
	}
}