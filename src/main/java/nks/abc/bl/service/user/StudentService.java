package nks.abc.bl.service.user;

import nks.abc.bl.view.object.objects.user.StudentView;

public interface StudentService {
	StudentView getById(Long id);
	void save(StudentView student);
	void delete(Long... ids);
}
