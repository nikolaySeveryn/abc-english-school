package nks.abc.dao.specification.user.student;

import nks.abc.dao.base.interfaces.CriterionSpecification;
import nks.abc.dao.specification.common.ByIdSpecification;

public class StudentSpecificationFactory {
	public CriterionSpecification byId(Long id){
		return new ByIdSpecification<Long>("id", id);
	}
}
