package nks.abc.dao.repository.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import nks.abc.dao.base.BaseRepositoryImpl;
import nks.abc.dao.specification.user.student.StudentSpecificationFactory;
import nks.abc.domain.user.Student;
import nks.abc.domain.user.impl.StudentImpl;

@Repository
public class StudentRepository extends BaseRepositoryImpl<Student> {

	@Autowired
	private StudentSpecificationFactory specificationFactory;
	
	public StudentRepository() {
		super(StudentImpl.class);
	}
	
	public StudentSpecificationFactory specifications(){
		return specificationFactory;
	}

}
