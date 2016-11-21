package nks.abc.domain.school.impl;

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
import javax.persistence.Transient;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.transaction.annotation.Transactional;

import nks.abc.dao.repository.user.StudentRepository;
import nks.abc.dao.specification.chunks.Specification;
import nks.abc.dao.specification.factory.user.GroupSpecificationFactory;
import nks.abc.domain.school.Group;
import nks.abc.domain.school.Level;
import nks.abc.domain.user.Student;
import nks.abc.domain.user.Teacher;
import nks.abc.domain.user.impl.StudentImpl;
import nks.abc.domain.user.impl.TeacherImpl;

@Entity
@Table(name="group")
@Configurable
public class GroupImpl implements Group {
	
	private final static Integer MULTIPLIER = 100;
	
	@Autowired
	@Transient
	private StudentRepository studentRepository;
	
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
	//TODO: plan
	
	@Override
	public Double getFloatTarif(){
		return (double)tarif / MULTIPLIER;
	}
	
	@Override
	public Boolean hasMembers(){
		return getStudents().size() > 0;
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
	@Transactional(readOnly=true)
	public List<Student> getStudents() {
		List<Student> students = new ArrayList<Student>();
		try{
			Specification specification = GroupSpecificationFactory.buildFactory("groups").byId(getId());
			students = studentRepository.query(specification);
		}
		catch (Exception e){
			//TODO:hadle exception
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
		return students;
	}

	@Override
	public String toString() {
		return "Group [id=" + id + ", name=" + name + ", level=" + level
				+ ", tarif=" + tarif + ", teacher=" + teacher + "]";
	}
	
	
}
