package nks.abc.dao.repository.user;

import org.springframework.stereotype.Repository;

import nks.abc.dao.base.BaseHibernrateRepositoryImpl;
import nks.abc.dao.specification.user.group.GroupSpecificationFactory;
import nks.abc.domain.entity.user.Group;

@Repository
public class GroupRepository extends BaseHibernrateRepositoryImpl<Group> {

	public GroupRepository() {
		super(Group.class);
	}
	
	public GroupSpecificationFactory getSpecificationFactory(){
		return new GroupSpecificationFactory();
	}
	
	
}
