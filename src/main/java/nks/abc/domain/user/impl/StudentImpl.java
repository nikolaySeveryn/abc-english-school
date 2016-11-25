package nks.abc.domain.user.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import nks.abc.domain.school.Group;
import nks.abc.domain.school.impl.GroupImpl;
import nks.abc.domain.user.Parent;
import nks.abc.domain.user.Student;


@Entity
@PrimaryKeyJoinColumn(name="id")
@OnDelete(action=OnDeleteAction.CASCADE)
@Table(name="student")
public class StudentImpl extends UserImpl implements Student {
	
	@OneToOne(fetch=FetchType.EAGER, cascade=CascadeType.ALL, targetEntity=ParentImpl.class)
	private Parent parent;
	@ManyToMany(fetch=FetchType.EAGER, targetEntity=GroupImpl.class)
	@JoinTable(name="student_group", 
		joinColumns={@JoinColumn(name="student_id")},
		inverseJoinColumns={@JoinColumn(name="group_id")})
	@Fetch(FetchMode.SELECT)
	private Set<Group> groups;
	@Column(nullable=false)
	private Integer discount = 0;
	@Column(nullable=false, name="money_balance")
	private Integer moneyBalance = 0;
	
	
	@Override
	public List<Group> getGroupsList() {
		return new ArrayList<Group>(getGroups());
	}
	@Override
	public void setGroupsList(List<Group> groups) {
		setGroups(new HashSet<Group>(groups));
	}
	
	@Override
	public Parent getParent() {
		return parent;
	}
	@Override
	public void setParent(Parent parent) {
		this.parent = parent;
	}
	@Override
	public Set<Group> getGroups() {
		return groups;
	}
	@Override
	public void setGroups(Set<? extends Group> group) {
		this.groups = (Set<Group>) group;
	}
	@Override
	public Integer getDiscount() {
		return discount;
	}
	@Override
	public void setDiscount(Integer discount) {
		this.discount = discount;
	}
	@Override
	public Integer getMoneyBalance() {
		return moneyBalance;
	}
	@Override
	public void setMoneyBalance(Integer moneyBalance) {
		this.moneyBalance = moneyBalance;
	}
	@Override
	public Long getAccountId() {
		return getAccount().getAccountId();
	}
	@Override
	public boolean isTeacher() {
		return false;
	}
	@Override
	public boolean isAdministrator() {
		return false;
	}
	@Override
	public boolean isStudent() {
		return true;
	}
}
