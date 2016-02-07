package nks.abc.domain.entity.user;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import nks.abc.web.validator.Email;


@Entity
public class ParentInfo {
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="parent_info_id_gen")
	@SequenceGenerator(name="parent_info_id_gen", allocationSize=1, sequenceName="parent_info_id_seq")
	private long id;
	private String firstName;
	private String sirName;
	private String patronomic;
	private String phoneNum;
	@Email
	private String email;
	private Date birthday;
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
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
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	
}
