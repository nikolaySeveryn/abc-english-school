package nks.abc.bl.service;

import java.util.List;

import nks.abc.bl.domain.user.Group;
import nks.abc.bl.view.object.objects.user.GroupView;

public interface GroupService {
	List<GroupView> getGroups();
	void saveGroup(GroupView group);
	GroupView getById(Long id);
	void deleteGroups(Long... ids);
}
