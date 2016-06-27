package nks.abc.dao.specification.user.account;

import nks.abc.dao.base.ConjunctionSpecification;
import nks.abc.dao.base.interfaces.CriterionSpecification;

public class AccountInfoSpecificationFactory {
	
	public CriterionSpecification byLoginAndDeleted(String login, Boolean deleted) {
		return new ConjunctionSpecification(new LoginSpecification(login), new IsDeletedSpecification(deleted));
	}
	
	public CriterionSpecification byIdAndDeleted(Long id, Boolean deleted){
		return new ConjunctionSpecification(new IdSpecification(id), new IsDeletedSpecification(deleted));
	}
	
	public CriterionSpecification byId(Long id){
		return new IdSpecification(id);
	}
	
	public CriterionSpecification byLogin(String login){
		return new LoginSpecification(login);
	}
	
	public CriterionSpecification byIsDeleted(Boolean isDeleted){
		return new IsDeletedSpecification(isDeleted);
	}
	
	@Override
	public boolean equals(Object other){
		// no state
		return true;
	}
	
	@Override
	public int hashCode() {
		return 1;
	}
}
