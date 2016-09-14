package nks.abc.dao.newspecification.user;

import nks.abc.dao.newspecification.base.EqualSpecification;
import nks.abc.dao.newspecification.base.HibernateAlias;
import nks.abc.dao.newspecification.base.HibernateSpecification;
import nks.abc.domain.user.Account;

public class AccountSpecifications {
	
	private static final String RELATIVE_FIELD = "accountInfo";
	private static final String ALIAS = "account";
	private static final Class<?> BASE_CLASS = Account.class;
	
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
		EqualSpecification spec = EqualsSpecificationWithAlias();
		spec.setCriteria("isDisable", ! is);
		return spec;
	}
	
	
	public static HibernateSpecification byId(Long id){
		return new EqualSpecification("id", id);
	}
	
	public static HibernateSpecification byEmail(String email) {
		EqualSpecification spec = EqualsSpecificationWithAlias();
		spec.setCriteria("email", email);
		return spec;
	}
	
	public static HibernateSpecification onlyStaff(){
		EqualSpecification spec = EqualsSpecificationWithAlias();
		//TODO: implement
		return spec;
	}
}
