package nks.abc.dao.repository.user;

import org.springframework.stereotype.Repository;

import nks.abc.dao.base.BaseHibernrateRepositoryImpl;
import nks.abc.dao.specification.user.student.StudentSpecificationFactory;
import nks.abc.domain.entity.user.Student;

@Repository
public class StudentRepository extends BaseHibernrateRepositoryImpl<Student> {

	public StudentRepository() {
		super(Student.class);
	}
	
	public StudentSpecificationFactory getSpecificationFactory(){
		return new StudentSpecificationFactory();
	}

}
