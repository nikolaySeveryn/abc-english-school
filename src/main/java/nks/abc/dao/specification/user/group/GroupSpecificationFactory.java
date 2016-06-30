package nks.abc.dao.specification.user.group;

import org.springframework.stereotype.Component;

import nks.abc.dao.base.interfaces.CriterionSpecification;
import nks.abc.dao.specification.common.ByIdSpecification;

@Component
public class GroupSpecificationFactory {

	public CriterionSpecification byId(Long id){
		return new ByIdSpecification<Long>("id", id);
	}
}
