package nks.abc.dao.specification.user.administrator;

import nks.abc.dao.base.CriterionSpecification;
import nks.abc.domain.entity.user.AccountInfo;

public class AdministratorSpecificationFactory {
	
	public CriterionSpecification byAccount(AccountInfo account) {
		return new ByAccountSpesification(account);
	}
	
	@Override
	public boolean equals(Object obj) {
		//No state
		return true;
	}
	
	@Override
	public int hashCode() {
		return 1;
	};
}
