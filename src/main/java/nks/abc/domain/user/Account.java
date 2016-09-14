package nks.abc.domain.user;


import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.validator.constraints.Email;

@Entity
@Table(name="accountinfo")
public class Account {
	
	private static final int MAX_PASS_LENGTH = 15;
	private static final int MIN_PASS_LENGTH = 6;

	@Transient
	private PasswordEncryptor passwordEncryptor = new PasswordEncryptor();
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="account_id_seq")
	@SequenceGenerator(name="account_id_seq", allocationSize=1, sequenceName="account_id_seq")
	private Long accountId;
	
	@Column(nullable=false, unique=true)
	@Email
	private String email;
	
	@Column(name="password", nullable=false)
	private String passwordHash;
	
	@OneToOne(cascade={CascadeType.ALL})
	@JoinColumn(name="personal_info")
	private PersonalInfo peronalInfo;
	
	@ElementCollection(targetClass=Role.class, fetch=FetchType.LAZY)
	@Enumerated(EnumType.STRING)
	@CollectionTable(name="user_role", joinColumns=@JoinColumn(name="accountinfo_accountid"))
	@Column(name="ROLE",nullable=false)
	private Set<Role> role = new HashSet<Role>();
	
	@Column(nullable=false)
	private Boolean isDisable=false;
	
	public void updatePassword(String password) {
        setPasswordHash(passwordEncryptor.encrypt(password));
	}
	
	public String updatePasswordToRandom() {
		String newPass = generateRandomString();
		updatePassword(newPass);
		return newPass;
	}
	
	private String generateRandomString() {
		SecureRandom random = new SecureRandom();
		String res = new BigInteger(130, random).toString(32);
		return res.substring(0, random.nextInt(MAX_PASS_LENGTH - MIN_PASS_LENGTH) + MIN_PASS_LENGTH);
	}
	
	public boolean isAdministrator() {
		return getRole().contains(Role.ADMINISTRATOR);
	}
	
	public boolean isTeacher(){
		return getRole().contains(Role.TEACHER);
	}
	
	public boolean isStudent(){
		return getRole().contains(Role.STUDENT);
	}
	
	public void setIsAdministrator(boolean is){
		if(is){
			role.add(Role.ADMINISTRATOR);
		}
		else {
			role.remove(Role.ADMINISTRATOR);
		}
	}
	
	public void setIsTeacher(boolean is){
		if(is){
			role.add(Role.TEACHER);
		}
		else {
			role.remove(Role.TEACHER);
		}
	}
	
	public void setIsStudent(boolean is){
		if(is){
			role.add(Role.STUDENT);
		}
		else {
			role.remove(Role.STUDENT);
		}
	}
	
	public String getFullName() {
		return peronalInfo.getFullName();
	}
	
	/*
	 * plain getters & setters
	 */
	public Long getAccountId() {
		return accountId;
	}
	public void setAccountId(Long id) {
		this.accountId = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	private Set<Role> getRole() {
		return role;
	}
	private void setRole(Set<Role> role) {
		this.role = role;
	}
	public String getPasswordHash() {
		return passwordHash;
	}
	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}
	public Boolean getIsDisable() {
		return isDisable;
	}
	public void setIsDisable(Boolean isDeleted) {
		this.isDisable = isDeleted;
	}
	public PersonalInfo getPeronalInfo() {
		return peronalInfo;
	}
	public void setPeronalInfo(PersonalInfo peronalInfo) {
		this.peronalInfo = peronalInfo;
	}

	@Override
	public String toString() {
		return "AccountInfo [accountId=" + accountId 
				+ ", passwordHash=" + passwordHash + ", peronalInfo="
				+ peronalInfo + ", role=" + role + ", isDeleted=" + isDisable
				+ ", super=" + super.toString() + ", email=" + email + "]";
	}
	
	
}

