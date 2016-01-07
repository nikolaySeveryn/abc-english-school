package nks.abc.domain.entity.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="USER")
public abstract class User {
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="user_id_gen")
	@SequenceGenerator(name="user_id_gen", allocationSize=1, sequenceName="user_id_seq")
	private Long id;
	private String login;
	@Column(name="password")
	private String passwordHash;
	@Enumerated(EnumType.STRING)
	private Role role;
	private String firstName;
	private String sirName;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getPassHash() {
		return passwordHash;
	}
	public void setPassHash(String passHash) {
		this.passwordHash = passHash;
	}
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getSirName() {
		return sirName;
	}
	public void setSirName(String sirName) {
		this.sirName = sirName;
	}
	
	
	
}
