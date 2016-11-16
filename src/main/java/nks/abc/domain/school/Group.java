package nks.abc.domain.school;

import java.util.List;
import java.util.Set;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import nks.abc.domain.user.Teacher;
import nks.abc.domain.user.impl.StudentImpl;

public interface Group {

	Double getFloatTarif();
	Boolean hasMembers();

	void setFloatTarif(Double tarif);

	Long getId();
	void setId(Long id);

	@NotNull
	@Size(min=1)
	String getName();
	void setName(String name);

	@NotNull
	Level getLevel();
	void setLevel(Level level);
	
	@NotNull
	Integer getTarif();
	void setTarif(Integer tarif);

	Teacher getTeacher();
	void setTeacher(Teacher teacher);

	Set<StudentImpl> getStudents();
	List<StudentImpl> getSortedStudents();
	void setStudents(Set<StudentImpl> students);

}