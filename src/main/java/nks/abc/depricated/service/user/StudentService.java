package nks.abc.depricated.service.user;

import nks.abc.domain.user.Student;

public interface StudentService {
	Student getById(Long id);
	void save(Student student);
	void delete(Long... ids);
}
