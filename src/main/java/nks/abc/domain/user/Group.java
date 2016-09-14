package nks.abc.domain.user;

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

import org.hibernate.annotations.OrderBy;

@Entity
public class Group {
	
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
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="teacher")
	private Teacher teacher;
	@ManyToMany(fetch=FetchType.LAZY, mappedBy="groups")
	private Set<Student> students = null;
	//TODO: plan
	
	public Double getFloatTarif(){
		return (double)tarif / MULTIPLIER;
	}
	public void setFloatTarif(Double tarif){
		this.tarif = (int) (tarif * MULTIPLIER);
	}
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
	public Integer getTarif() {
		return tarif;
	}
	public void setTarif(Integer tarif) {
		this.tarif = tarif;
	}
	public Teacher getTeacher() {
		return teacher;
	}
	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}
	
	public Set<Student> getStudents() {
		return students;
	}
	public List<Student>getSortedStudents(){
		List<Student> studentsList = new ArrayList<Student>(students);
		Collections.sort(studentsList, new Comparator<Student>() {
			@Override
			public int compare(Student student1, Student student2) {
				return student1.getUserId().compareTo(student2.getUserId());
			}
		});
		return studentsList;
	}
	public void setStudents(Set<Student> students) {
		this.students = students;
	}
	@Override
	public String toString() {
		return "Group [id=" + id + ", name=" + name + ", level=" + level
				+ ", tarif=" + tarif + ", teacher=" + teacher + ", students="
				+ students + "]";
	}
	
	
}
