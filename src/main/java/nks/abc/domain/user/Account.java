package nks.abc.domain.user;

import nks.abc.domain.validation.annotation.Email;

public interface Account {

	Teacher getTeacherData();
	Administrator getAdministratorData();
	void updatePassword(String password);
	String updatePasswordToRandom();
	boolean getIsAdministrator();
	boolean getIsTeacher();
	boolean getIsStudent();
	void setIsAdministrator(boolean is);
	void setIsTeacher(boolean is);
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