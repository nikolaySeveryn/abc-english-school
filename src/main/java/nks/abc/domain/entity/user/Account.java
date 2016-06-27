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
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.varia.FallbackErrorHandler;
import org.hibernate.validator.constraints.Email;

@Entity
@Table(name="accountinfo")
public class Account {
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="account_id_seq")
	@SequenceGenerator(name="account_id_seq", allocationSize=1, sequenceName="account_id_seq")
	private Long accountId;
	
	@Column(nullable=false, unique=true)
	private String login;
	
	@Column(name="password", nullable=false)
	private String passwordHash;
	
//	@OneToOne(cascade={CascadeType.PERSIST, CascadeType.MERGE})
	@OneToOne(cascade={CascadeType.ALL})
	@JoinColumn(name="personal_info")
	private PersonalInfo peronalInfo;
	
	@ElementCollection(targetClass=Role.class, fetch=FetchType.LAZY)
	@Enumerated(EnumType.STRING)
	@CollectionTable(name="user_role", joinColumns=@JoinColumn(name="accountinfo_accountid"))
	@Column(name="ROLE",nullable=false)
	private Set<Role> role = new HashSet<Role>();
	@Column(nullable=false)
	private Boolean isDeleted=false;
	
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
	public String getPasswordHash() {
		return passwordHash;
	}
	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}
	public Boolean getIsDeleted() {
		return isDeleted;
	}
	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
	public PersonalInfo getPeronalInfo() {
		return peronalInfo;
	}
	public void setPeronalInfo(PersonalInfo peronalInfo) {
		this.peronalInfo = peronalInfo;
	}

	@Override
	public String toString() {
		return "AccountInfo [accountId=" + accountId + ", login=" + login
				+ ", passwordHash=" + passwordHash + ", peronalInfo="
				+ peronalInfo + ", role=" + role + ", isDeleted=" + isDeleted
				+ ", super=" + super.toString() + "]";
	}
	
	
}

