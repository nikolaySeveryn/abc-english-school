package nks.abc.dao.specification.user.account;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import nks.abc.dao.base.interfaces.CriterionSpecification;

public class IdSpecification implements CriterionSpecification {
	
	private Long id;

	public IdSpecification(Long id) {
		this.id = id;
	}

	@Override
	public Criterion toCriteria() {
		return Restrictions.eq("accountId", id);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		IdSpecification other = (IdSpecification) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
