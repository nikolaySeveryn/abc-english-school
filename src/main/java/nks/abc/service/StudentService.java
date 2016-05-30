package nks.abc.service;

import nks.abc.domain.view.user.StudentView;

public interface StudentService {
	StudentView getById(Long id);
	void save(StudentView student);
	void delete(Long... ids);
}
