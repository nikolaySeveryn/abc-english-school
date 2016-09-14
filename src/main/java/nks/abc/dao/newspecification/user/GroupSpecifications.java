package nks.abc.dao.newspecification.user;

import nks.abc.dao.newspecification.base.EqualSpecification;
import nks.abc.dao.newspecification.base.HibernateAlias;
import nks.abc.dao.newspecification.base.HibernateSpecification;
import nks.abc.domain.user.Group;

public class GroupSpecifications {
	private static final String ID_FIELD = "id";
	private static final String RELATIVE_FIELD = "group";
	private static final String ALIAS = "group";
	private static final Class<?> BASE_CLASS = Group.class;
	
	private static HibernateAlias getAlias() {
		return new HibernateAlias(RELATIVE_FIELD, ALIAS, BASE_CLASS);
	}
	
	private static EqualSpecification EqualsSpecificationWithAlias() {
		EqualSpecification specification = new EqualSpecification();
		specification.setAlias(getAlias());
		return specification;
	}
	
	public static HibernateSpecification byId(Long id) {
		EqualSpecification spec = EqualsSpecificationWithAlias();
		spec.setCriteria(ID_FIELD, id);
		return spec;
	}
}
