package nks.abc.dao.repository.user;

import org.springframework.stereotype.Repository;

import nks.abc.dao.base.BaseRepositoryImpl;
import nks.abc.domain.user.Teacher;
import nks.abc.domain.user.impl.TeacherImpl;

@Repository
public class TeacherRepository extends BaseRepositoryImpl<Teacher>{
	
	public TeacherRepository() {
		super(TeacherImpl.class);
	}
}
