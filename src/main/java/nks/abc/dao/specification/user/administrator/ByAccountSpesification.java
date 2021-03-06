package nks.abc.dao.specification.user.administrator;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import nks.abc.dao.base.CriterionSpecification;
import nks.abc.domain.entity.user.AccountInfo;

class ByAccountSpesification implements CriterionSpecification{

	private AccountInfo account;

	
	public ByAccountSpesification(AccountInfo account) {
		this.account = account;
	}

	@Override
	public Criterion toCriteria() {
		return Restrictions.eq("accountInfo", account);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((account == null) ? 0 : account.hashCode());
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
		ByAccountSpesification other = (ByAccountSpesification) obj;
		if (account == null) {
			if (other.account != null)
				return false;
		} else if (!account.equals(other.account))
			return false;
		return true;
	}
	
	
}
