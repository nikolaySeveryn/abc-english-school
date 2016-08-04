package nks.abc.bl.domain.user;

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
public class Student extends User {
	
	
	@OneToOne(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	private Parrent parent;
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name="student_group", 
		joinColumns={@JoinColumn(name="student_id")},
		inverseJoinColumns={@JoinColumn(name="group_id")})
	private Set<Group> groups;
	@Column(nullable=false)
	private Integer discount = 0;
	@Column(nullable=false)
	private Integer moneyBalance = 0;
	
	public Parrent getParent() {
		return parent;
	}
	public void setParent(Parrent parent) {
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
