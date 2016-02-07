package nks.abc.domain.entity.user;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.SequenceGenerator;

import org.hibernate.validator.constraints.Email;


@Entity
public class Student implements User{
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="student_id_gen")
	@SequenceGenerator(name="student_id_gen", allocationSize=1, sequenceName="student_id_seq")
	private Long id;
	@OneToOne(fetch=FetchType.LAZY)
	private ParentInfo parent;
	@ManyToOne
	@JoinColumn(name="groupId", nullable=false)
	private Group group;
	private Integer discount;
	private Integer moneyBalance;
	@ManyToOne(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	@JoinColumn(name="account_info", nullable=false)
	private AccountInfo accountInfo;
	
	
	
	public ParentInfo getParent() {
		return parent;
	}
	public void setParent(ParentInfo parent) {
		this.parent = parent;
	}
	public Group getGroup() {
		return group;
	}
	public void setGroup(Group group) {
		this.group = group;
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
	public AccountInfo getAccountInfo() {
		return accountInfo;
	}
	public void setAccountInfo(AccountInfo accountInfo) {
		this.accountInfo = accountInfo;
	}
	@Override
	public Long getAccountId() {
		return getAccountInfo().getAccountId();
	}
}
