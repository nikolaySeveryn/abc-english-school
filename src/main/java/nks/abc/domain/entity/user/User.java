package nks.abc.domain.entity.user;

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

@Entity
@Inheritance(strategy=InheritanceType.JOINED)
public abstract class User {
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="user_id_gen")
	@SequenceGenerator(name="user_id_gen", allocationSize=1, sequenceName="user_id_seq")
	private Long id;
	
	
	@ManyToOne(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@JoinColumn(name="account_info", nullable=false)
	private Account accountInfo;
	
	//TODO: move to factory
	public static Student newStudent() {
		Student instance = new Student();
		instance.setGroups(new HashSet<Group>());
		instance.setAccountInfo(new Account());
		return instance;
	}
	
	public void updatePassword(String password) {
		accountInfo.updatePassword(password);
	}
	
	public Boolean isNew() {
		return id == null || id < 1;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
		return "User [id=" + id + ", accountInfo=" + accountInfo
				+ ", super =" + super.toString() + "]";
	}
	
	
}