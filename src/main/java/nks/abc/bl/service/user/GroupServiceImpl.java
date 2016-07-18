package nks.abc.bl.service.user;

import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import nks.abc.bl.domain.user.Group;
import nks.abc.bl.view.converter.Converter;
import nks.abc.bl.view.converter.ConvertersHolder;
import nks.abc.bl.view.converter.user.AccountViewConverter;
import nks.abc.bl.view.converter.user.StudentViewConverter;
import nks.abc.bl.view.object.objects.user.GroupView;
import nks.abc.core.exception.handler.ErrorHandler;
import nks.abc.core.exception.repository.RepositoryException;
import nks.abc.core.exception.service.ServiceException;
import nks.abc.dao.repository.user.GroupRepository;

@Service
@Transactional(readOnly = true)
public class GroupServiceImpl implements GroupService {

	private static final Logger log = Logger.getLogger(GroupServiceImpl.class);

	@Autowired
	private GroupRepository groupDAO;

	@Autowired
	private Converter<Group,GroupView> converter;
	
	private ErrorHandler errorHandler;
	
	@Autowired
	@Qualifier("serviceErrorHandler")
	public void setErrorHandler(ErrorHandler errorHandler){
		this.errorHandler = errorHandler;
		this.errorHandler.loggerFor(this.getClass());
	}

	@Override
	public List<GroupView> getGroups() {
		List<GroupView> groupsDto = null;
		try {
			List<Group> groups = groupDAO.getAll();
			converter.setRelativeConvertersPattern(new StudentViewConverter(), new AccountViewConverter());
			groupsDto = converter.toView(groups);
		}
		catch (Exception e) {
			errorHandler.handle(e, "get all groups");
		}
		return groupsDto;
	}

	@Override
	@Transactional(readOnly = false)
	public void saveGroup(GroupView dto) {
		converter.setRelativeConvertersPattern(new AccountViewConverter());
		Group group = converter.toDomain(dto);
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
			errorHandler.handle(e, "save group: " + dto);
		}
	}

	@Override
	public GroupView getById(Long id) {
		try {
			Group bo = getDomainGroupById(id);
			return converter.toView(bo);
		}
		catch (Exception e) {
			errorHandler.handle(e, "get group by id = " + id);
			return null;
		}
	}

	private Group getDomainGroupById(Long id) {
		return groupDAO.uniqueQuery(groupDAO.specifications().byId(id));
	}

	@Override
	@Transactional(readOnly = false)
	public void deleteGroups(Long... ids) {
		try {
			for (Long id : ids) {
				groupDAO.delete(getDomainGroupById(id));
			}
		}
		catch (Exception e) {
			errorHandler.handle(e, "delete groups: " + Arrays.toString(ids));
		}
	}
}
