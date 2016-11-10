package nks.abc.depricated.service.user;

import java.util.List;

import nks.abc.domain.user.Group;
import nks.abc.domain.user.impl.GroupImpl;

public interface GroupService {
	List<Group> getGroups();
	void saveGroup(Group group);
	Group getById(Long id);
	void deleteGroups(Long... ids);
}
