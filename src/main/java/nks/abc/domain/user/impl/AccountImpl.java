package nks.abc.domain.user.impl;


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
import javax.validation.constraints.Pattern;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.validator.constraints.Email;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import nks.abc.core.exception.handler.ErrorHandler;
import nks.abc.dao.newspecification.user.AccountSpecifications;
import nks.abc.dao.repository.user.AdminRepository;
import nks.abc.dao.repository.user.TeacherRepository;
import nks.abc.domain.user.Account;
import nks.abc.domain.user.Administrator;
import nks.abc.domain.user.PersonalInfo;
import nks.abc.domain.user.Role;
import nks.abc.domain.user.Teacher;
import nks.abc.domain.validation.annotation.Unique;

@Entity
@Table(name="accountinfo")
@Configurable
public class AccountImpl implements Account {
	
	private static final int MAX_PASS_LENGTH = 15;
	private static final int MIN_PASS_LENGTH = 6;
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="account_id_seq")
	@SequenceGenerator(name="account_id_seq", allocationSize=1, sequenceName="account_id_seq")
	private Long accountId;
	
	@Column(nullable=false, unique=true)
	private String email;
	
	@Column(name="password", nullable=false)
	@Pattern(regexp="^.{5,}$", message="Minimum size 5 symbols")
	private String passwordHash;
	
	@OneToOne(cascade={CascadeType.ALL}, fetch=FetchType.EAGER,targetEntity=PersonalInfoImpl.class)
	@JoinColumn(name="personal_info")
	private PersonalInfo peronalInfo;
	
	@ElementCollection(targetClass=Role.class, fetch = FetchType.EAGER)
	@Enumerated(EnumType.STRING)
	@CollectionTable(name="user_role", joinColumns=@JoinColumn(name="accountinfo_accountid"))
	@Column(name="ROLE",nullable=false)
	@Fetch(FetchMode.SELECT)
	private Set<Role> roles = new HashSet<Role>();
	
	@Column(nullable=false)
	private Boolean active=true;
	
	@Autowired
	@Transient
	private TeacherRepository teacherRepository;
	
	@Autowired
	@Transient
	private AdminRepository adminRepository;

	@Transient
	private PasswordEncryptor passwordEncryptor = new PasswordEncryptor();
	
	@Transient
	private ErrorHandler errorHandler;
	
	@Autowired
	@Qualifier("serviceErrorHandler")
	public void setErrorHandler(ErrorHandler errorHandler){
		this.errorHandler = errorHandler;
		this.errorHandler.loggerFor(this.getClass());
	}
	
	@Override
	@Transactional(readOnly=true)
	public Teacher getTeacherData(){
		System.out.println("teacher repo: " + teacherRepository);
		if(!roles.contains(Role.TEACHER)){
			return null;
		}
		Teacher teacherData = null;
		try {
			 teacherData = teacherRepository.uniqueQuery(AccountSpecifications.byId(accountId));
		}
		catch(Exception e){
			errorHandler.handle(e);
		}
		return teacherData;
	}
	
	@Override
	@Transactional(readOnly=true)
	public Administrator getAdministratorData() {
		if(!roles.contains(Role.ADMINISTRATOR)){
			return null;
		}
		Administrator adminData = null;
		try{
			adminData = adminRepository.uniqueQuery(AccountSpecifications.byId(accountId));
		}
		catch(Exception e){
			errorHandler.handle(e);
		}
		
		return adminData;
	}
	
	@Override
	public void updatePassword(String password) {
        setPasswordHash(passwordEncryptor.encrypt(password));
	}
	
	@Override
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
	
	@Override
	public boolean getIsAdministrator() {
		return getRoles().contains(Role.ADMINISTRATOR);
	}
	
	@Override
	public boolean getIsTeacher(){
		return getRoles().contains(Role.TEACHER);
	}
	
	@Override
	public boolean getIsStudent(){
		return getRoles().contains(Role.STUDENT);
	}
	
	@Override
	public void setIsAdministrator(boolean is){
		if(is){
			roles.add(Role.ADMINISTRATOR);
		}
		else {
			roles.remove(Role.ADMINISTRATOR);
		}
	}
	
	@Override
	public void setIsTeacher(boolean is){
		if(is){
			roles.add(Role.TEACHER);
		}
		else {
			roles.remove(Role.TEACHER);
		}
	}
	
	@Override
	public void setIsStudent(boolean is){
		if(is){
			roles.add(Role.STUDENT);
		}
		else {
			roles.remove(Role.STUDENT);
		}
	}
	
	@Override
	public String getFullName() {
		return peronalInfo.makeFullName();
	}
	
	/*
	 * plain getters & setters
	 */
	@Override
	public Long getAccountId() {
		return accountId;
	}
	@Override
	public void setAccountId(Long id) {
		this.accountId = id;
	}
	@Override
	public String getEmail() {
		return email;
	}
	@Override
	public void setEmail(String email) {
		this.email = email;
	}
	private Set<Role> getRoles() {
		return roles;
	}
	private void setRoles(Set<Role> role) {
		this.roles = role;
	}
	@Override
	public String getPasswordHash() {
		return passwordHash;
	}
	@Override
	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}
	@Override
	public Boolean getIsActive() {
		return active;
	}
	@Override
	public void setIsActive(Boolean isFired) {
		this.active = isFired;
	}
	@Override
	public PersonalInfo getPeronalInfo() {
		return peronalInfo;
	}
	@Override
	public void setPeronalInfo(PersonalInfo peronalInfo) {
		this.peronalInfo = peronalInfo;
	}

	@Override
	public String toString() {
		return "AccountInfo [accountId=" + accountId 
				+ ", passwordHash=" + passwordHash + ", peronalInfo="
				+ peronalInfo + ", role=" + roles + ", isFired=" + active
				 + ", email=" + email + "]";
	}
	
	
}
