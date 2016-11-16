package nks.abc.dao.repository.user;

import org.springframework.stereotype.Repository;

import nks.abc.dao.base.BaseRepositoryImpl;
import nks.abc.domain.school.Group;
import nks.abc.domain.school.impl.GroupImpl;

@Repository
public class GroupRepository extends BaseRepositoryImpl<Group> {
	

	public GroupRepository() {
		super(GroupImpl.class);
	}
}
