package nks.abc.domain.user.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.OrderBy;

import nks.abc.domain.user.Group;
import nks.abc.domain.user.Level;
import nks.abc.domain.user.Teacher;

@Entity
@Table(name="group")
public class GroupImpl implements Group {
	
	private final static Integer MULTIPLIER = 100; 
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="group_id_gen")
	@SequenceGenerator(name="group_id_gen", allocationSize=1, sequenceName="group_id_seq")
	private Long id;
	@Column(nullable=false)
	private String name;
	@Column(nullable=false)
	@Enumerated(EnumType.STRING)
	private Level level;
	@Column(nullable=false)
	private Integer tarif;
	@ManyToOne(fetch=FetchType.EAGER, targetEntity=TeacherImpl.class)
	@JoinColumn(name="teacher")
	private Teacher teacher;
	@ManyToMany(fetch=FetchType.EAGER, mappedBy="groups", targetEntity=StudentImpl.class)
	@Fetch(FetchMode.SELECT)
	private Set<StudentImpl> students = null;
	//TODO: plan
	
	@Override
	public Double getFloatTarif(){
		return (double)tarif / MULTIPLIER;
	}
	
	@Override
	public Boolean hasMembers(){
		return students.size() > 0;
	}
	
	@Override
	public void setFloatTarif(Double tarif){
		this.tarif = (int) (tarif * MULTIPLIER);
	}
	@Override
	public Long getId() {
		return id;
	}
	@Override
	public void setId(Long id) {
		this.id = id;
	}
	@Override
	public String getName() {
		return name;
	}
	@Override
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public Level getLevel() {
		return level;
	}
	@Override
	public void setLevel(Level level) {
		this.level = level;
	}
	@Override
	public Integer getTarif() {
		return tarif;
	}
	@Override
	public void setTarif(Integer tarif) {
		this.tarif = tarif;
	}
	@Override
	public Teacher getTeacher() {
		return teacher;
	}
	@Override
	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}
	
	@Override
	public Set<StudentImpl> getStudents() {
		return students;
	}
	@Override
	public List<StudentImpl>getSortedStudents(){
		List<StudentImpl> studentsList = new ArrayList<StudentImpl>(students);
		Collections.sort(studentsList, new Comparator<StudentImpl>() {
			@Override
			public int compare(StudentImpl student1, StudentImpl student2) {
				return student1.getUserId().compareTo(student2.getUserId());
			}
		});
		return studentsList;
	}
	@Override
	public void setStudents(Set<StudentImpl> students) {
		this.students = students;
	}
	@Override
	public String toString() {
		return "Group [id=" + id + ", name=" + name + ", level=" + level
				+ ", tarif=" + tarif + ", teacher=" + teacher + ", students="
				+ students + "]";
	}
	
	
}
