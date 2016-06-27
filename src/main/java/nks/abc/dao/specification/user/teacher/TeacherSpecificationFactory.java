package nks.abc.dao.specification.user.teacher;

import nks.abc.dao.base.interfaces.CriterionSpecification;
import nks.abc.dao.base.interfaces.HQLSpecification;
import nks.abc.domain.entity.user.Account;

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
