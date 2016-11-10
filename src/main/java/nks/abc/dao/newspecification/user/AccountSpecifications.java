package nks.abc.dao.newspecification.user;

import nks.abc.dao.newspecification.base.EqualSpecification;
import nks.abc.dao.newspecification.base.HibernateAlias;
import nks.abc.dao.newspecification.base.HibernateSpecification;
import nks.abc.domain.user.impl.AccountImpl;

public class AccountSpecifications {
	
	private static final String RELATIVE_FIELD = "accountInfo";
	private static final String ALIAS = "account";
	private static final Class<?> BASE_CLASS = AccountImpl.class;
	
	private static HibernateAlias getAlias() {
		return new HibernateAlias(RELATIVE_FIELD, ALIAS, BASE_CLASS);
	}
	
	private static EqualSpecification EqualsSpecificationWithAlias() {
		EqualSpecification specification = new EqualSpecification();
		specification.setAlias(getAlias());
		
		return specification;
	}
	
	public static HibernateSpecification active() {
		return active(true);
	}
	
	public static HibernateSpecification active(Boolean is) {
		EqualSpecification specification = EqualsSpecificationWithAlias();
		specification.setCriteria("active", is);
		return specification;
	}
	
	
	public static HibernateSpecification byId(Long id) {
		EqualSpecification specification = EqualsSpecificationWithAlias();
		specification.setCriteria("id", id);
		return specification;
	}
	
	public static HibernateSpecification byEmail(String email) {
		EqualSpecification specification = EqualsSpecificationWithAlias();
		specification.setCriteria("email", email);
		return specification;
	}
	
	public static HibernateSpecification onlyStaff(){
		EqualSpecification spec = EqualsSpecificationWithAlias();
		//TODO: implement
		return spec;
	}
}
