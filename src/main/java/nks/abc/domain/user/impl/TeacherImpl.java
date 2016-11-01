package nks.abc.domain.user.impl;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import nks.abc.domain.user.Teacher;

@Entity
@Table(name="teacher")
@PrimaryKeyJoinColumn(name="id")
@OnDelete(action=OnDeleteAction.CASCADE)
public class TeacherImpl extends StaffImpl implements Teacher {
	
}