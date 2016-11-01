package nks.abc.domain.user.impl;



import javax.annotation.ManagedBean;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import nks.abc.domain.user.PersonalInfo;

@Entity
@Table(name="personalinfo")
@ManagedBean
public class PersonalInfoImpl implements PersonalInfo {
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="personal_info_id_gen")
	@SequenceGenerator(name="personal_info_id_gen", allocationSize=1, sequenceName="personal_info_id_seq")
	private Long id;
	private String firstName;
	private String sirName;
	private String patronomic;
	private java.sql.Date birthday;
	private String phoneNum=null;
	
	
	public static PersonalInfo getNew() {
		return new PersonalInfoImpl();
	}
	
	@Override
	public Long getId(){
		return id;
	}
	
	@Override
	public void setId(Long id){
		this.id = id;
	}
	
	@Override
	public String makeFullName(){
		return sirName + " " + firstName + " " + patronomic;
	}

	@Override
	public String getFirstName() {
		return firstName;
	}
	@Override
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	@Override
	public String getSirName() {
		return sirName;
	}
	@Override
	public void setSirName(String sirName) {
		this.sirName = sirName;
	}
	
	@Override
	public java.sql.Date getBirthday() {
		return birthday;
	}
	@Override
	public void setBirthday(java.sql.Date birthday) {
		this.birthday = birthday;
	}
	
	@Override
	public java.util.Date getBirthdayUtilDate(){
		return getBirthday();
	}
	
	@Override
	public void setBirthdayUtilDate(java.util.Date birthday) {
		if(birthday == null) {
			setBirthday(null);
			return;
		}
		java.sql.Date sqlDate = new java.sql.Date(birthday.getTime());
		setBirthday(sqlDate);
	}
	@Override
	public String getPhoneNum() {
		return phoneNum;
	}
	@Override
	public void setPhoneNum(String phoneNum) {
		if(phoneNum != null && phoneNum.length()==0) {
			setPhoneNum(null);
		}
		else{
			this.phoneNum = phoneNum;
		}
	}

	@Override
	public String getPatronomic() {
		return patronomic;
	}

	@Override
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