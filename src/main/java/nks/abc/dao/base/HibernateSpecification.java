package nks.abc.dao.base;

import org.hibernate.criterion.Criterion;

public interface HibernateSpecification {
	Criterion toCriteria();
}
