package nks.abc.dao.specification.user.administrator;

import org.springframework.stereotype.Component;

import nks.abc.bl.domain.user.Account;
import nks.abc.dao.base.interfaces.CriterionSpecification;

@Component
public class AdministratorSpecificationFactory {
	
	public CriterionSpecification byAccount(Account account) {
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
