package nks.abc.dao.specification.user.group;

import nks.abc.dao.base.CriterionSpecification;
import nks.abc.dao.specification.common.ByIdSpecification;

public class GroupSpecificationFactory {

	public CriterionSpecification byId(Long id){
		return new ByIdSpecification<Long>("id", id);
	}
}
