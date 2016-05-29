package nks.abc.service;

import java.util.List;

import nks.abc.domain.dto.user.GroupDTO;
import nks.abc.domain.entity.user.Group;

public interface GroupService {
	List<GroupDTO> getGroups();
	void saveGroup(GroupDTO group);
	GroupDTO getById(Long id);
	void deleteGroups(Long... ids);
}
