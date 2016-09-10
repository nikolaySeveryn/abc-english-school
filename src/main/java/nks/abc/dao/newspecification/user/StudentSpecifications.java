package nks.abc.dao.newspecification.user;

import nks.abc.dao.newspecification.base.EqualSpecification;
import nks.abc.dao.newspecification.base.HibernateSpecification;

public class StudentSpecifications {
	private static final String ID_FIELD = "id";

	public static HibernateSpecification byId(Long id){
		return new EqualSpecification(ID_FIELD, id);
	}
}
