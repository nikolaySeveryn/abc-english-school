package nks.abc.dao.specification.common;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import nks.abc.dao.base.interfaces.CriterionSpecification;

public class ByIdSpecification<T extends Number> implements CriterionSpecification {
	
	private final T id;
	private final String idFieldName;
	
	public ByIdSpecification(String idFieldName,T id) {
		this.id = id;
		this.idFieldName = idFieldName;
	}

	@Override
	public Criterion toCriteria() {
		return Restrictions.eq(idFieldName, id);
	}

}
