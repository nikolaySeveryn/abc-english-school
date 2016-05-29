package nks.abc.dao.specification.user.account;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import nks.abc.dao.base.CriterionSpecification;

class IsDeletedSpecification implements CriterionSpecification {

	private Boolean isDelete;
	
	IsDeletedSpecification(Boolean isDelete) {
		this.isDelete = isDelete;
	}
	

	@Override
	public Criterion toCriteria() {
		return Restrictions.eqOrIsNull("isDeleted", isDelete);
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((isDelete == null) ? 0 : isDelete.hashCode());
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
		IsDeletedSpecification other = (IsDeletedSpecification) obj;
		if (isDelete == null) {
			if (other.isDelete != null)
				return false;
		} else if (!isDelete.equals(other.isDelete))
			return false;
		return true;
	}
}
