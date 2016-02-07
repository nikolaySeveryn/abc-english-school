package nks.abc.dao.specification.user.teacher;

import nks.abc.dao.base.HibernateSpecification;
import nks.abc.domain.entity.user.AccountInfo;

public class TeacherSpecificationFactory {

	public HibernateSpecification byAccount(AccountInfo account){
		return new ByAccountSpecification(account);
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
