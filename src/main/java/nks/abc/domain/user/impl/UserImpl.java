package nks.abc.domain.user.impl;

import java.util.HashSet;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import nks.abc.domain.user.Account;
import nks.abc.domain.user.User;

@Entity
@Table(name="user")
@Inheritance(strategy=InheritanceType.JOINED)
public abstract class UserImpl implements User {
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="user_id_gen")
	@SequenceGenerator(name="user_id_gen", allocationSize=1, sequenceName="user_id_seq")
	private Long userId;
	
	
	@ManyToOne(fetch=FetchType.EAGER, targetEntity=AccountImpl.class)
	@JoinColumn(name="account_info", nullable=false)
	@OnDelete(action=OnDeleteAction.CASCADE)
	private Account accountInfo;
	
	//TODO: move to factory
	public static Student newStudent() {
		Student instance = new Student();
		instance.setGroups(new HashSet<Group>());
		instance.setAccountInfo(new AccountImpl());
		instance.getAccountInfo().setIsStudent(true);
		return instance;
	}
	
	@Override
	public void updatePassword(String password) {
		accountInfo.updatePassword(password);
	}
	
	@Override
	public Boolean isNew() {
		return userId == null || userId < 1;
	}
	
	@Override
	public Long getUserId() {
		return userId;
	}
	@Override
	public void setUserId(Long id) {
		this.userId = id;
	}
	
	@Override
	public Account getAccountInfo() {
		return accountInfo;
	}
	@Override
	public void setAccountInfo(Account accountInfo) {
		this.accountInfo = accountInfo;
	}
	@Override
	public Long getAccountId() {
		return getAccountInfo().getAccountId();
	}

	@Override
	public String toString() {
		return "User [id=" + userId + ", accountInfo=" + accountInfo
				+ ", super =" + super.toString() + "]";
	}
	
	
}