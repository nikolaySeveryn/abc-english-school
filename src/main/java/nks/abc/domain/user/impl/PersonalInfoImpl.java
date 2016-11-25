package nks.abc.domain.user.impl;

import javax.annotation.ManagedBean;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import nks.abc.domain.user.PersonalInfo;

@Entity
@Table(name="personal_info")
@ManagedBean
public class PersonalInfoImpl implements PersonalInfo {
	
	private final static String DEFAULT_FULLNAME_SEPARATOR = " ";
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="personal_info_id_gen")
	@SequenceGenerator(name="personal_info_id_gen", allocationSize=1, sequenceName="personal_info_id_seq")
	private Long id;
	@Column(name="firtst_name")
	private String firstName;
	@Column(name="sir_name")
	private String sirName;
	private String patronomic;
	private java.sql.Date birthday;
	@Column(unique=true, name="phone_number")
	private String phoneNumber=null;
	
	
	@Override
	public Long getId(){
		return id;
	}
	
	@Override
	public void setId(Long id){
		this.id = id;
	}
	
	@Override
	public String getFullName(){
		return getFullName(DEFAULT_FULLNAME_SEPARATOR);
	}
	
	@Override
	public String getFullName(String separator){
		return sirName + separator + firstName + separator + patronomic;
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
	public String getPhoneNumber() {
		return phoneNumber;
	}
	@Override
	public void setPhoneNumber(String phoneNum) {
		if(phoneNum != null && phoneNum.length()==0) {
			setPhoneNumber(null);
		}
		else{
			this.phoneNumber = phoneNum;
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
				+ ", birthday=" + birthday + ", phoneNum=" + phoneNumber + "]";
	}
	
	
}
