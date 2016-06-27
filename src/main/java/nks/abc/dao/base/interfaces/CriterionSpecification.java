package nks.abc.dao.base.interfaces;

import org.hibernate.criterion.Criterion;

public interface CriterionSpecification {
	Criterion toCriteria();
}
