package nks.abc.domain.user;

import org.hibernate.validator.constraints.Email;

public interface Parent {

	Long getId();
	void setId(Long id);

	PersonalInfo getPersonalInfo();
	void setPersonalInfo(PersonalInfo personalInfo);
	
	@Email
	String getEmail();
	void setEmail(String email);

}