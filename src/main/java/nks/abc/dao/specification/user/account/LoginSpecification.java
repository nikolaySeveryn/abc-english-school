package nks.abc.dao.specification.user.account;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import nks.abc.dao.base.CriterionSpecification;

class LoginSpecification implements CriterionSpecification{

	private String login;
	
	LoginSpecification(String login) {
		super();
		this.login = login;
	}

	@Override
	public Criterion toCriteria() {
		return Restrictions.eq("login", login);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((login == null) ? 0 : login.hashCode());
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
		LoginSpecification other = (LoginSpecification) obj;
		if (login == null) {
			if (other.login != null)
				return false;
		} else if (!login.equals(other.login))
			return false;
		return true;
	}

}
