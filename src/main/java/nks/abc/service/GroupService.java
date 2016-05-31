package nks.abc.service;

import java.util.List;

import nks.abc.domain.entity.user.Group;
import nks.abc.domain.view.object.objects.user.GroupView;

public interface GroupService {
	List<GroupView> getGroups();
	void saveGroup(GroupView group);
	GroupView getById(Long id);
	void deleteGroups(Long... ids);
}
