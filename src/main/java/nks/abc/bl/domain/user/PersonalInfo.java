package nks.abc.bl.domain.user;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.validator.constraints.Email;

@Entity
public class PersonalInfo {
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="personal_info_id_gen")
	@SequenceGenerator(name="personal_info_id_gen", allocationSize=1, sequenceName="personal_info_id_seq")
	private Long id;
	private String firstName;
	private String sirName;
	private String patronomic;
	private Date birthday;
	private String phoneNum;
	
	
	public static PersonalInfo getNew() {
		return new PersonalInfo();
	}
	
	public Long getId(){
		return id;
	}
	
	public void setId(Long id){
		this.id = id;
	}
	
	public String getFullName(){
		return sirName + " " + firstName + " " + patronomic;
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

	public String getPatronomic() {
		return patronomic;
	}

	public void setPatronomic(String patronomic) {
		this.patronomic = patronomic;
	}
	@Override
	public String toString() {
		return "PersonalInfo [id=" + id + ", firstName=" + firstName
				+ ", sirName=" + sirName + ", patronomic=" + patronomic
				+ ", birthday=" + birthday + ", phoneNum=" + phoneNum + "]";
	}
	
	
}
