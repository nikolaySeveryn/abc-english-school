package nks.abc.bl.view.object.objects.user;

import java.util.Date;

import javax.annotation.ManagedBean;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import nks.abc.bl.domain.user.Account;
import nks.abc.bl.view.validation.annotation.Email;
import nks.abc.bl.view.validation.annotation.Unique;

@ManagedBean
public abstract class UserView {
	private Long accountId;
	private Long personalInfoId;
	@Email
	@Unique(entity=Account.class, field="email")
	private String email;
	@Pattern(regexp="^.{5,}$", message="Minimum size 5 symbols")
	private String password;
	private String passwordHash;
	private String firstName;
	private String sirName;
	private String patronomic;
	@Past(message="He didn't born yet!")
	private Date birthday;
	@Pattern(regexp="^\\+?([0-9]|\\-){0,15}$", message="Avaliable symbols: '+'(at begining), 0-9,'-'. Maximum size 15 symbols")
	private String phoneNum;
	private Boolean isDisabled;
	
	
	/**
	 * getters & setters
	 * */
	public Long getAccountId() {
		return accountId;
	}
	public void setAccountId(Long id) {
		this.accountId = id;
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
	
	public Boolean getIsDisabled() {
		return isDisabled;
	}
	public void setIsDisabled(Boolean isDeleted) {
		this.isDisabled = isDeleted;
	}
	public Long getPersonalInfoId() {
		return personalInfoId;
	}
	public void setPersonalInfoId(Long personalInfoId) {
		this.personalInfoId = personalInfoId;
	}

	@Override
	public String toString() {
		return "UserDTO [id=" + accountId + ", personalInfoId=" + personalInfoId + ", password="
				+ password + ", passwordHash=" + passwordHash + ", firstName="
				+ firstName + ", sirName=" + sirName + ", patronomic="
				+ patronomic + ", birthday=" + birthday + ", phoneNum="
				+ phoneNum + ", email=" + email + ", isDisabled=" + isDisabled
				+ "]";
	}
}
