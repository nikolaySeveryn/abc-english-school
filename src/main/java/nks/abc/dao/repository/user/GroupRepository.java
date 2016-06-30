package nks.abc.dao.repository.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import nks.abc.dao.base.BaseRepositoryImpl;
import nks.abc.dao.specification.user.group.GroupSpecificationFactory;
import nks.abc.domain.entity.user.Group;

@Repository
public class GroupRepository extends BaseRepositoryImpl<Group> {
	
	@Autowired
	private GroupSpecificationFactory specificationFactory;

	public GroupRepository() {
		super(Group.class);
	}
	
	public GroupSpecificationFactory specifications(){
		return specificationFactory;
	}
	
	
}
