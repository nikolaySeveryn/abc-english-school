package nks.abc.domain.dto.user;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.ManagedBean;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


import nks.abc.domain.entity.user.Role;
import nks.abc.web.validator.Email;

@ManagedBean
public abstract class UserDTO {
	private Long id;
	@Pattern(regexp="^([a-z]|[A-Z]|[0-9]|[_-]){5,}", message="Avaliable symbols: a-z,A-Z,0-9,'_','-'. Minimum size 5 symbol")
	private String login;
	@Pattern(regexp="^.{7,}$", message="Minimum size 7 symbols")
	private String password;
	private String passwordHash;
	private Set<Role> role = new HashSet<Role>();
	private String firstName;
	private String sirName;
	@Past(message="I don't belive you")
	private Date birthday;
	@Pattern(regexp="^\\+?([0-9]|\\-){0,15}$", message="Avaliable symbols: '+'(at begining), 0-9,'-'. Maximum size 15 symbols")
	private String phoneNum;
	@Email
	private String email;
	
	
	
	
	/**
	 * getters & setters
	 * */
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
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPasswordHash() {
		return passwordHash;
	}
	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}
	public Set<Role> getRole() {
		return role;
	}
	public void setRole(Set<Role>role) {
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
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public String getPhoneNum() {
		return phoneNum;
	}
	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	@Override
	public String toString() {
		return "UserDTO [id=" + id + ", login=" + login + ", password="
				+ password + ", passwordHash=" + passwordHash + ", role="
				+ role + ", firstName=" + firstName + ", sirName=" + sirName
				+ ", birthday=" + birthday + ", phoneNum=" + phoneNum
				+ ", email=" + email + ", parrent=" + super.toString() + "]";
	}

	
	
}
