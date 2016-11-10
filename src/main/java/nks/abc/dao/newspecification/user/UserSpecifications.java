package nks.abc.dao.newspecification.user;

import nks.abc.dao.newspecification.base.EqualSpecification;
import nks.abc.dao.newspecification.base.HibernateAlias;
import nks.abc.dao.newspecification.base.HibernateSpecification;
import nks.abc.domain.user.impl.UserImpl;

public class UserSpecifications {
	private static final String ID_FIELD = "userId";
	private static final String RELATIVE_FIELD = "user";
	private static final String ALIAS = "user";
	private static final Class<?> BASE_CLASS = UserImpl.class;
	
	//TODO: move all this shit from static
	private static HibernateAlias getAlias() {
		return new HibernateAlias(RELATIVE_FIELD, ALIAS, BASE_CLASS);
	}
	
	private static EqualSpecification EqualsSpecificationWithAlias() {
		EqualSpecification specification = new EqualSpecification();
		specification.setAlias(getAlias());
		
		return specification;
	}
	
	public static HibernateSpecification ById(Long id){
//		EqualSpecification spec = EqualsSpecificationWithAlias();
		EqualSpecification spec = new EqualSpecification();
		spec.setCriteria(ID_FIELD, id);
		return spec;
	}
}
