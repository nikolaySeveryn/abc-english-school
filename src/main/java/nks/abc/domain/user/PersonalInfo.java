package nks.abc.domain.user;

import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

public interface PersonalInfo {

	String getFullName();
	String getFullName(final String separator);
	
	Long getId();
	void setId(Long id);
	
	String getFirstName();
	void setFirstName(String firstName);

	String getSirName();
	void setSirName(String sirName);
	
	@Past(message="He didn't born yet!")
	java.sql.Date getBirthday();
	void setBirthday(java.sql.Date birthday);
	@Past(message="He didn't born yet!")
	java.util.Date getBirthdayUtilDate();
	void setBirthdayUtilDate(java.util.Date birthday);
	
	@Pattern(regexp="^\\+380\\([0-9]{2}\\)[0-9]{7}$", message="Supported only Ukrainian phone number")
	String getPhoneNumber();
	void setPhoneNumber(String phoneNum);

	String getPatronomic();
	void setPatronomic(String patronomic);

}