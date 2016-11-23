package nks.abc.domain.user.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import nks.abc.dao.base.RepositoryException;
import nks.abc.dao.repository.user.GroupRepository;
import nks.abc.dao.specification.chunks.Specification;
import nks.abc.dao.specification.factory.user.TeacherSpecificationFactory;
import nks.abc.domain.school.Group;
import nks.abc.domain.user.Teacher;

@Entity
@Table(name="teacher")
@PrimaryKeyJoinColumn(name="id")
@OnDelete(action=OnDeleteAction.CASCADE)
@Configurable
public class TeacherImpl extends StaffImpl implements Teacher {
	
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
			//TODO:hadle exception
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return groups;
	}

	@Override
	public Long countOfGroup() {
		try{
			return (long) retreiveGroups().size();
//			return groupRepository.count(byIdSpecification());
		}
		catch (RepositoryException e){
			//TODO:handel exception
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	private Specification byIdSpecification() {
		return TeacherSpecificationFactory.buildFactory().byId(getUserId());
	}
}