package nks.abc.dao.specification.user.account;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import nks.abc.dao.base.interfaces.CriterionSpecification;

class IsDisableSpecification implements CriterionSpecification {

	private Boolean isDisable;
	
	IsDisableSpecification(Boolean isDelete) {
		this.isDisable = isDelete;
	}
	

	@Override
	public Criterion toCriteria() {
		return Restrictions.eqOrIsNull("isDisable", isDisable);
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((isDisable == null) ? 0 : isDisable.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		IsDisableSpecification other = (IsDisableSpecification) obj;
		if (isDisable == null) {
			if (other.isDisable != null)
				return false;
		} else if (!isDisable.equals(other.isDisable))
			return false;
		return true;
	}
}
