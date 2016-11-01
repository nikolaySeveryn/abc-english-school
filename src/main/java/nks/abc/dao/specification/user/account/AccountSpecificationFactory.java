package nks.abc.dao.specification.user.account;

import org.springframework.stereotype.Component;

import nks.abc.dao.base.ConjunctionSpecification;
import nks.abc.dao.base.interfaces.CriterionSpecification;

@Component
public class AccountSpecificationFactory {
	
	public CriterionSpecification byEmailAndIsActive(String login, Boolean disable) {
		return new ConjunctionSpecification(new EmailSpecification(login), new IsActiveSpecification(disable));
	}
	
	public CriterionSpecification byIdAndIsActive(Long id, Boolean disable){
		return new ConjunctionSpecification(new IdSpecification(id), new IsActiveSpecification(disable));
	}
	
	public CriterionSpecification byId(Long id){
		return new IdSpecification(id);
	}
	
	public CriterionSpecification byEmail(String login){
		return new EmailSpecification(login);
	}
	
	public CriterionSpecification byIsActive(Boolean isDeleted){
		return new IsActiveSpecification(isDeleted);
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
