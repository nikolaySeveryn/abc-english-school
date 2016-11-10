package nks.abc.dao.repository.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import nks.abc.dao.base.BaseRepositoryImpl;
import nks.abc.dao.specification.user.group.GroupSpecificationFactory;
import nks.abc.domain.user.Group;
import nks.abc.domain.user.impl.GroupImpl;

@Repository
public class GroupRepository extends BaseRepositoryImpl<Group> {
	
	@Autowired
	private GroupSpecificationFactory specificationFactory;

	public GroupRepository() {
		super(GroupImpl.class);
	}
	
	public GroupSpecificationFactory specifications(){
		return specificationFactory;
	}
	
	
}
