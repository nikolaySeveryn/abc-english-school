package nks.abc.domain.user;

import java.util.Set;

import nks.abc.domain.validation.annotation.Email;

public interface Account {

	User getUser();
	void updatePassword(String password);
	String updatePasswordToRandom();
	String getFullName();

	/*
	 * plain getters & setters
	 */
	Long getAccountId();
	void setAccountId(Long id);
	@Email
	String getEmail();
	void setEmail(String email);
	String getPasswordHash();
	void setPasswordHash(String passwordHash);
	Boolean getIsActive();
	void setIsActive(Boolean isFired);
	PersonalInfo getPeronalInfo();
	void setPeronalInfo(PersonalInfo peronalInfo);
	public Set<Role> getRoles();
	public void setRoles(Set<Role> roles);
}