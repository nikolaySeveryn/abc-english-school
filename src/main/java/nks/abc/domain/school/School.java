package nks.abc.domain.school;

import java.util.List;

import nks.abc.domain.school.impl.GroupImpl;
import nks.abc.domain.user.Student;

public interface School {
	List<Group> getGroups();
	void saveGroup(Group group);
	Group findGroupById(Long id);
	void deleteGroups(Long... ids);
	Student findStudentById(Long id);
	void saveStudent(Student student);
	void deleteStudents(Long... ids);
}
