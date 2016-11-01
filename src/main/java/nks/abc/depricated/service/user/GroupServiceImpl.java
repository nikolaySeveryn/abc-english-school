package nks.abc.depricated.service.user;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import nks.abc.core.exception.handler.ErrorHandler;
import nks.abc.dao.newspecification.user.GroupSpecifications;
import nks.abc.dao.repository.user.GroupRepository;
import nks.abc.domain.user.impl.Group;

@Service
@Transactional(readOnly = true)
public class GroupServiceImpl implements GroupService {

	private static final Logger log = Logger.getLogger(GroupServiceImpl.class);

	@Autowired
	private GroupRepository groupDAO;

	private ErrorHandler errorHandler;
	
	@Autowired
	@Qualifier("serviceErrorHandler")
	public void setErrorHandler(ErrorHandler errorHandler){
		this.errorHandler = errorHandler;
		this.errorHandler.loggerFor(this.getClass());
	}

	@Override
	public List<Group> getGroups() {
		List<Group> groups = new ArrayList<Group>();
		try {
			 groups = groupDAO.retrieveAll();
		}
		catch (Exception e) {
			errorHandler.handle(e, "get all groups");
		}
		return groups;
	}

	@Override
	@Transactional(readOnly = false)
	public void saveGroup(Group group) {
		try {
			if (group.getId() != null && group.getId() > 0L) {
				log.info("updating group: " + group);
				groupDAO.update(group);
			}
			else {
				log.info("inserting group: " + group);
				groupDAO.insert(group);
			}
		}
		catch (Exception e) {
			errorHandler.handle(e, "save group: " + group);
		}
	}

	@Override
	public Group getById(Long id) {
		try {
			Group group = groupDAO.uniqueQuery(GroupSpecifications.byId(id));
			return group;
		}
		catch (Exception e) {
			errorHandler.handle(e, "get group by id = " + id);
			return null;
		}
	}

	@Override
	@Transactional(readOnly = false)
	public void deleteGroups(Long... ids) {
		try {
			for (Long id : ids) {
				groupDAO.delete(getById(id));
			}
		}
		catch (Exception e) {
			errorHandler.handle(e, "delete groups: " + Arrays.toString(ids));
		}
	}
}
