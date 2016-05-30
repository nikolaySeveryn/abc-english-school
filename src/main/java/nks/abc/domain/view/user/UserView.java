package nks.abc.domain.view.user;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.ManagedBean;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import nks.abc.domain.entity.user.Role;
import nks.abc.web.common.validator.Email;

@ManagedBean
public abstract class UserView {
	private Long accountId;
	private Long personalInfoId;
	@Pattern(regexp="^([a-z]|[A-Z]|[0-9]|[_-]){5,}", message="Avaliable symbols: a-z,A-Z,0-9,'_','-'. Minimum size 5 symbol")
	private String login;
	@Pattern(regexp="^.{7,}$", message="Minimum size 7 symbols")
	private String password;
	private String passwordHash;
	private String firstName;
	private String sirName;
	private String patronomic;
	@Past(message="He didn't born yet!")
	private Date birthday;
	@Pattern(regexp="^\\+?([0-9]|\\-){0,15}$", message="Avaliable symbols: '+'(at begining), 0-9,'-'. Maximum size 15 symbols")
	private String phoneNum;
	@Email
	private String email;
	private Boolean isDeleted;
	
	public static StudentView newStudent(){
		StudentView instance = new StudentView();
		instance.setGroups(new ArrayList<GroupView>());
		instance.setParent(new ParentInfoView());
		instance.setIsDeleted(false);
		instance.setMoneyBalance(0);
		return instance;
	}
	
	
	/**
	 * getters & setters
	 * */
	public Long getAccountId() {
		return accountId;
	}
	public void setAccountId(Long id) {
		this.accountId = id;
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
	
	public String getPatronomic() {
		return patronomic;
	}
	public void setPatronomic(String patronomic) {
		this.patronomic = patronomic;
	}
	
	public Boolean getIsDeleted() {
		return isDeleted;
	}
	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
	public Long getPersonalInfoId() {
		return personalInfoId;
	}
	public void setPersonalInfoId(Long personalInfoId) {
		this.personalInfoId = personalInfoId;
	}

	@Override
	public String toString() {
		return "UserDTO [id=" + accountId + ", personalInfoId=" + personalInfoId + ", login=" + login + ", password="
				+ password + ", passwordHash=" + passwordHash + ", firstName="
				+ firstName + ", sirName=" + sirName + ", patronomic="
				+ patronomic + ", birthday=" + birthday + ", phoneNum="
				+ phoneNum + ", email=" + email + ", isDeleted=" + isDeleted
				+ "]";
	}
}
