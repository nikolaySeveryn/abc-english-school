package nks.abc.dao.base;

import org.hibernate.criterion.Criterion;

public interface CriterionSpecification {
	Criterion toCriteria();
}
