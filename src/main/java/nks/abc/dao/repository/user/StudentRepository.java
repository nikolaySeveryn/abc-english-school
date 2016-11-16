package nks.abc.dao.repository.user;

import org.springframework.stereotype.Repository;

import nks.abc.dao.base.BaseRepositoryImpl;
import nks.abc.domain.user.Student;
import nks.abc.domain.user.impl.StudentImpl;

@Repository
public class StudentRepository extends BaseRepositoryImpl<Student> {

	public StudentRepository() {
		super(StudentImpl.class);
	}

}
