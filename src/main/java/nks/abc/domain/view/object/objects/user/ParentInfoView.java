package nks.abc.domain.view.object.objects.user;

import java.sql.Date;

import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import nks.abc.domain.view.validation.Email;


public class ParentInfoView {
	
	private Long id;
	private String firstName;
	private String sirName;
	private String patronomic;
	@Past(message="He didn't born yet!")
	private Date birthday;
	@Pattern(regexp="^\\+?([0-9]|\\-){0,15}$", message="Avaliable symbols: '+'(at begining), 0-9,'-'. Maximum size 15 symbols")
	private String phoneNum;
	@Email(notEmpty=false)
	private String email;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	public String getPatronomic() {
		return patronomic;
	}
	public void setPatronomic(String patronomic) {
		this.patronomic = patronomic;
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
		return "PersonalInfoDTO [id=" + id + ", firstName=" + firstName
				+ ", sirName=" + sirName + ", patronomic=" + patronomic
				+ ", birthday=" + birthday + ", phoneNum=" + phoneNum
				+ ", email=" + email + "]";
	}
	
}
