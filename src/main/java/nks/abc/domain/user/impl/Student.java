package nks.abc.domain.user.impl;

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

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;


@Entity
@PrimaryKeyJoinColumn(name="id")
@OnDelete(action=OnDeleteAction.CASCADE)
public class Student extends UserImpl {
	
	
	@OneToOne(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	private Parent parent;
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name="student_group", 
		joinColumns={@JoinColumn(name="student_id")},
		inverseJoinColumns={@JoinColumn(name="group_id")})
	private Set<Group> groups;
	@Column(nullable=false)
	private Integer discount = 0;
	@Column(nullable=false)
	private Integer moneyBalance = 0;
	
	public Parent getParent() {
		return parent;
	}
	public void setParent(Parent parent) {
		this.parent = parent;
	}
	public Set<Group> getGroups() {
		return groups;
	}
	public void setGroups(Set<Group> group) {
		this.groups = group;
	}
	public Integer getDiscount() {
		return discount;
	}
	public void setDiscount(Integer discount) {
		this.discount = discount;
	}
	public Integer getMoneyBalance() {
		return moneyBalance;
	}
	public void setMoneyBalance(Integer moneyBalance) {
		this.moneyBalance = moneyBalance;
	}
	@Override
	public Long getAccountId() {
		return getAccountInfo().getAccountId();
	}
}
