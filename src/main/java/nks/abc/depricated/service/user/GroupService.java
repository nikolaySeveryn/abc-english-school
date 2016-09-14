package nks.abc.depricated.service.user;

import java.util.List;

import nks.abc.depricated.view.object.objects.user.GroupView;
import nks.abc.domain.user.Group;

public interface GroupService {
	List<GroupView> getGroups();
	void saveGroup(GroupView group);
	GroupView getById(Long id);
	void deleteGroups(Long... ids);
}
