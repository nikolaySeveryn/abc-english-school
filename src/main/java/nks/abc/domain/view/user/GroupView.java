package nks.abc.domain.view.user;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import nks.abc.domain.entity.user.Level;

@ManagedBean
public class GroupView {
	private Long id;
	@NotNull
	@Size(min=1)
	private String name;
	@NotNull
	private Level level;
	private Double tarif;
	private StaffView teacher;
	private List<StudentView> students = new ArrayList<StudentView>();
	
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
	public StaffView getTeacher() {
		return teacher;
	}
	public void setTeacher(StaffView teacher) {
		this.teacher = teacher;
	}
	
	public List<StudentView> getStudents() {
		return students;
	}
	public void setStudents(List<StudentView> students) {
		this.students = students;
	}
	@Override
	public String toString() {
		return "GroupDTO [id=" + id + ", name=" + name + ", level=" + level
				+ ", tarif=" + tarif + ", teacher=" + teacher + ", students="
				+ students + "]";
	}
}
