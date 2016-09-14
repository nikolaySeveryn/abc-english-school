package nks.abc.domain.user;

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

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Inheritance(strategy=InheritanceType.JOINED)
public abstract class User {
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="user_id_gen")
	@SequenceGenerator(name="user_id_gen", allocationSize=1, sequenceName="user_id_seq")
	private Long userId;
	
	
	@ManyToOne(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@JoinColumn(name="account_info", nullable=false)
	@OnDelete(action=OnDeleteAction.CASCADE)
	private Account accountInfo;
	
	//TODO: move to factory
	public static Student newStudent() {
		Student instance = new Student();
		instance.setGroups(new HashSet<Group>());
		instance.setAccountInfo(new Account());
		instance.getAccountInfo().setIsStudent(true);
		return instance;
	}
	
	public void updatePassword(String password) {
		accountInfo.updatePassword(password);
	}
	
	public Boolean isNew() {
		return userId == null || userId < 1;
	}
	
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long id) {
		this.userId = id;
	}
	
	public Account getAccountInfo() {
		return accountInfo;
	}
	public void setAccountInfo(Account accountInfo) {
		this.accountInfo = accountInfo;
	}
	public Long getAccountId() {
		return getAccountInfo().getAccountId();
	}

	@Override
	public String toString() {
		return "User [id=" + userId + ", accountInfo=" + accountInfo
				+ ", super =" + super.toString() + "]";
	}
	
	
}