package nks.abc.domain.user;

import nks.abc.domain.validation.annotation.Email;

public interface Account {

	User getUser();
	void updatePassword(String password);
	String updatePasswordToRandom();
	@Deprecated
	boolean getIsAdministrator();
	@Deprecated
	boolean getIsTeacher();
	@Deprecated
	boolean getIsStudent();
	@Deprecated
	void setIsAdministrator(boolean is);
	@Deprecated
	void setIsTeacher(boolean is);
	@Deprecated
	void setIsStudent(boolean is);
	String getFullName();

	/*
	 * plain getters & setters
	 */
	Long getAccountId();
	void setAccountId(Long id);
	@Email
//	@Unique(entity=AccountImpl.class, field="email")
	String getEmail();
	void setEmail(String email);
	String getPasswordHash();
	void setPasswordHash(String passwordHash);
	Boolean getIsActive();
	void setIsActive(Boolean isFired);
	PersonalInfo getPeronalInfo();
	void setPeronalInfo(PersonalInfo peronalInfo);
}