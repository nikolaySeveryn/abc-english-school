package nks.abc.domain.user.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.log4j.Logger;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import nks.abc.dao.base.RepositoryException;
import nks.abc.dao.repository.user.GroupRepository;
import nks.abc.dao.specification.chunks.Specification;
import nks.abc.dao.specification.factory.user.TeacherSpecificationFactory;
import nks.abc.domain.exception.CrudException;
import nks.abc.domain.school.Group;
import nks.abc.domain.user.Teacher;

@Entity
@Table(name="teacher")
@PrimaryKeyJoinColumn(name="id")
@OnDelete(action=OnDeleteAction.CASCADE)
@Configurable
public class TeacherImpl extends StaffImpl implements Teacher {
	
	private final static Logger log = Logger.getLogger(TeacherImpl.class);
	
	@Autowired
	@Transient
	private GroupRepository groupRepository;

	@Override
	public boolean isTeacher() {
		return true;
	}

	@Override
	public boolean isAdministrator() {
		return false;
	}

	@Override
	public List<Group> retreiveGroups() {
		List<Group>groups = new ArrayList<Group>();
		try{
			groups = groupRepository.query(byIdSpecification());
		}
		catch(RepositoryException e){
			log.error("error on retreiving teacher groups, group id:" + getAccountId(), e);
			throw new CrudException("error on retreiving teacher groups", e);
		}
		return groups;
	}

	@Override
	public Long countOfGroup() {
		return (long) retreiveGroups().size();
	}

	private Specification byIdSpecification() {
		return TeacherSpecificationFactory.buildFactory().byId(getUserId());
	}
}