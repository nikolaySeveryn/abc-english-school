package nks.abc.dao.specification.user.teacher;

import nks.abc.dao.base.CriterionSpecification;
import nks.abc.dao.base.HQLSpecification;
import nks.abc.domain.entity.user.AccountInfo;

public class TeacherSpecificationFactory {

	public CriterionSpecification byAccount(AccountInfo account){
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
