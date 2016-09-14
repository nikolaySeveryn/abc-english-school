package nks.abc.depricated.service.user;

import nks.abc.depricated.view.object.objects.user.StudentView;

public interface StudentService {
	StudentView getById(Long id);
	void save(StudentView student);
	void delete(Long... ids);
}
