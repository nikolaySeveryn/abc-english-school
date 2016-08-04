package nks.abc.dao.specification.user.account;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import nks.abc.dao.base.interfaces.CriterionSpecification;

class EmailSpecification implements CriterionSpecification{

	private String email;
	
	EmailSpecification(String email) {
		super();
		this.email = email;
	}

	@Override
	public Criterion toCriteria() {
		return Restrictions.eq("email", email);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
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
		EmailSpecification other = (EmailSpecification) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		return true;
	}

}
