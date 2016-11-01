package nks.abc.dao.specification.user.account;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import nks.abc.dao.base.interfaces.CriterionSpecification;

class IsActiveSpecification implements CriterionSpecification {

	private Boolean isActive;
	
	IsActiveSpecification(Boolean isActive) {
		this.isActive = isActive;
	}
	

	@Override
	public Criterion toCriteria() {
		return Restrictions.eqOrIsNull("active", isActive);
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((isActive == null) ? 0 : isActive.hashCode());
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
		IsActiveSpecification other = (IsActiveSpecification) obj;
		if (isActive == null) {
			if (other.isActive != null)
				return false;
		} else if (!isActive.equals(other.isActive))
			return false;
		return true;
	}
}
