package nks.abc.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import nks.abc.dao.exception.DAOException;
import nks.abc.dao.repository.user.GroupRepository;
import nks.abc.domain.entity.user.Group;
import nks.abc.domain.view.converter.Converter;
import nks.abc.domain.view.converter.ConvertersHolder;
import nks.abc.domain.view.converter.user.AccountViewConverter;
import nks.abc.domain.view.converter.user.StudentViewConverter;
import nks.abc.domain.view.object.objects.user.GroupView;
import nks.abc.service.GroupService;
import nks.abc.service.exception.ServiceException;

@Service
@Transactional(readOnly = true)
public class GroupServiceImpl implements GroupService {

	private static final Logger log = Logger.getLogger(GroupServiceImpl.class);

	@Autowired
	private GroupRepository groupDAO;

	@Autowired
	private Converter<Group,GroupView> converter;

	@Override
	public List<GroupView> getGroups() {
		try {
			List<Group> groups = groupDAO.getAll();
			converter.setRelativeConvertersPattern(new StudentViewConverter(), new AccountViewConverter());
			List<GroupView> groupsDto = converter.toView(groups);
			return groupsDto;
		}
		catch (DAOException de) {
			log.error("DAO Error during getting groups", de);
			throw new ServiceException("DAO Error during getting groups", de);
		}
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
		catch (DAOException de) {
			log.error("DAO Error during saving group", de);
			throw new ServiceException("DAO Error during saving group", de);
		}
	}

	@Override
	public GroupView getById(Long id) {
		try {
			Group bo = getDomainGroupById(id);
			return converter.toView(bo);
		}
		catch (DAOException de) {
			log.error("DAO Error during getting group by id", de);
			throw new ServiceException("DAO Error during getting group by id", de);
		}
	}

	private Group getDomainGroupById(Long id) {
		return groupDAO.uniqueQuery(groupDAO.getSpecificationFactory().byId(id));
	}

	@Override
	@Transactional(readOnly = false)
	public void deleteGroups(Long... ids) {
		try {
			for (Long id : ids) {
				groupDAO.delete(getDomainGroupById(id));
			}
		}
		catch (DAOException de) {
			//TODO: refactoring
			log.error("DAO Error during deleting group", de);
			throw new ServiceException("DAO Error during deleting group", de);
		}
	}
}
