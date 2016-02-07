package nks.abc.domain.entity.user;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

@Entity
public class Teacher implements User{
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="teacher_id_gen")
	@SequenceGenerator(name="teacher_id_gen", allocationSize=1, sequenceName="teacher_id_seq")
	private Long id;
	@ManyToOne(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	@JoinColumn(name="account_info", nullable=false)
	private AccountInfo accountInfo;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	@Override
	public String toString() {
		return "Teacher [id=" + id + ", accountInfo=" + accountInfo + "]";
	}
	
}
