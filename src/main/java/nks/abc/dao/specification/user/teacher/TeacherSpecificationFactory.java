package nks.abc.dao.specification.user.teacher;

import org.springframework.stereotype.Component;

import nks.abc.bl.domain.user.Account;
import nks.abc.dao.base.interfaces.CriterionSpecification;
import nks.abc.dao.base.interfaces.HQLSpecification;

@Component
public class TeacherSpecificationFactory {

	public CriterionSpecification byAccount(Account account){
		return new ByAccountSpecification(account);
	}
	
	public HQLSpecification byIsDeleted(Boolean isDeleted){
		return new ByIsDeletedSpecification(isDeleted);
	}
	
	
	@Override
	public boolean equals(Object obj) {
		//no state
		return true;
	}
	
	@Override
	public int hashCode() {
		return 1;
	}
}
