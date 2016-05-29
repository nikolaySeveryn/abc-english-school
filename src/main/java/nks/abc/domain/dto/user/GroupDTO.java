package nks.abc.domain.dto.user;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import nks.abc.domain.entity.user.Level;

@ManagedBean
public class GroupDTO {
	private Long id;
	@NotNull
	@Size(min=1)
	private String name;
	@NotNull
	private Level level;
	private Double tarif;
	private StaffDTO teacher;
	private List<StudentDTO> students = new ArrayList<StudentDTO>();
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Level getLevel() {
		return level;
	}
	public void setLevel(Level level) {
		this.level = level;
	}
	public Double getTarif() {
		return tarif;
	}
	public void setTarif(Double tarif) {
		this.tarif = tarif;
	}
	public StaffDTO getTeacher() {
		return teacher;
	}
	public void setTeacher(StaffDTO teacher) {
		this.teacher = teacher;
	}
	
	public List<StudentDTO> getStudents() {
		return students;
	}
	public void setStudents(List<StudentDTO> students) {
		this.students = students;
	}
	@Override
	public String toString() {
		return "GroupDTO [id=" + id + ", name=" + name + ", level=" + level
				+ ", tarif=" + tarif + ", teacher=" + teacher + ", students="
				+ students + "]";
	}
}
