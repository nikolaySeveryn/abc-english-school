package nks.abc.dao.repository.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import nks.abc.bl.domain.user.Teacher;
import nks.abc.dao.base.BaseRepositoryImpl;
import nks.abc.dao.specification.user.teacher.TeacherSpecificationFactory;

@Repository
public class TeacherRepository extends BaseRepositoryImpl<Teacher>{

	@Autowired
	private TeacherSpecificationFactory specificationFactory;
	
	public TeacherRepository() {
		super(Teacher.class);
	}
	
	public TeacherSpecificationFactory specifications(){
		return specificationFactory;
	}
}
