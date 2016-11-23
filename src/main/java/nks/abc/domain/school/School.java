package nks.abc.domain.school;

import java.util.List;

import nks.abc.domain.errors.ErrorsSet;
import nks.abc.domain.user.Student;

public interface School {
	List<Group> getAllGroups();
	void saveGroup(Group group);
	Group findGroupById(Long id);
	ErrorsSet<Group>checkGroupDelete(Long id);
	void deleteGroup(Long id);
	Student findStudentById(Long id);
	void saveStudent(Student student);
	void deleteStudents(Long... ids);
}
