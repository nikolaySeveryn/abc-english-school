package nks.abc.dao.repository.user;

import org.springframework.stereotype.Repository;

import nks.abc.dao.base.BaseHibernrateRepositoryImpl;
import nks.abc.dao.specification.user.teacher.TeacherSpecificationFactory;
import nks.abc.domain.entity.user.Teacher;

@Repository
public class TeacherRepository extends BaseHibernrateRepositoryImpl<Teacher>{

	public TeacherRepository() {
		super(Teacher.class);
	}
	
	public TeacherSpecificationFactory getSpecificaitonFactory(){
		return new TeacherSpecificationFactory();
	}
}
