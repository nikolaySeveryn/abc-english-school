package nks.abc.dao.repository.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import nks.abc.bl.domain.user.Student;
import nks.abc.dao.base.BaseRepositoryImpl;
import nks.abc.dao.specification.user.student.StudentSpecificationFactory;

@Repository
public class StudentRepository extends BaseRepositoryImpl<Student> {

	@Autowired
	private StudentSpecificationFactory specificationFactory;
	
	public StudentRepository() {
		super(Student.class);
	}
	
	public StudentSpecificationFactory specifications(){
		return specificationFactory;
	}

}
