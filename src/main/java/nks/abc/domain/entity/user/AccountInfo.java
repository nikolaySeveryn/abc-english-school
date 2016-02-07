package nks.abc.domain.entity.user;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
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
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.apache.commons.codec.binary.Base64;
import org.hibernate.validator.constraints.Email;

@Entity
public class AccountInfo {
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="user_id_gen")
	@SequenceGenerator(name="user_id_gen", allocationSize=1, sequenceName="user_id_seq")
	private Long accountId;
	@Column(nullable=false, unique=true)
	private String login;
	@Column(name="password", nullable=false)
	private String passwordHash;
	
	@ElementCollection(targetClass=Role.class, fetch=FetchType.LAZY)
	@Enumerated(EnumType.STRING)
	@CollectionTable(name="user_role")
	@Column(name="ROLE",nullable=false)
	private Set<Role> role = new HashSet<Role>();
	
	@Column(nullable=false)
	private String firstName;
	@Column(nullable=false)
	private String sirName;
	private String patronomic;
	private Date birthday;
	private String phoneNum;
	@Email
	private String email;
	private Boolean isDeleted;
	
	public void updatePassword(String password) {
		
		byte[] bytesOfPassword = password.getBytes();
		MessageDigest md=null;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e2) {
			e2.printStackTrace();
		}
		byte[] md5 = md.digest(bytesOfPassword);
		byte[] base64 = Base64.encodeBase64(md5);
		
        setPasswordHash(new String(base64));
	}
	
	public boolean isAdministrator(){
		return getRole().contains(Role.ADMINISTRATOR);
	}
	
	public boolean isTeacher(){
		return getRole().contains(Role.TEACHER);
	}
	
	public boolean isStudent(){
		return getRole().contains(Role.STUDENT);
	}
	
	public void isAdministrator(boolean is){
		if(is){
			role.add(Role.ADMINISTRATOR);
		}
		else {
			role.remove(Role.ADMINISTRATOR);
		}
	}
	
	public void isTeacher(boolean is){
		if(is){
			role.add(Role.TEACHER);
		}
		else {
			role.remove(Role.TEACHER);
		}
	}
	
	public void isStudent(boolean is){
		if(is){
			role.add(Role.STUDENT);
		}
		else {
			role.remove(Role.STUDENT);
		}
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
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	private Set<Role> getRole() {
		return role;
	}
	private void setRole(Set<Role> role) {
		this.role = role;
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
	public String getPasswordHash() {
		return passwordHash;
	}
	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	public Boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public String getPatronomic() {
		return patronomic;
	}

	public void setPatronomic(String patronomic) {
		this.patronomic = patronomic;
	}

	@Override
	public String toString() {
		return "AccountInfo [accountId=" + accountId + ", login=" + login
				+ ", passwordHash=" + passwordHash + ", role=" + role
				+ ", firstName=" + firstName + ", sirName=" + sirName
				+ ", patronomic=" + patronomic + ", birthday=" + birthday
				+ ", phoneNum=" + phoneNum + ", email=" + email
				+ ", isDeleted=" + isDeleted + "]";
	}
	
}

