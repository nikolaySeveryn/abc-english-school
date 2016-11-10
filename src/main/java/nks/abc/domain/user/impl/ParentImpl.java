package nks.abc.domain.user.impl;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import nks.abc.domain.user.Parent;
import nks.abc.domain.user.PersonalInfo;


@Entity
@Table(name="parent")
public class ParentImpl implements Parent {
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="parrent_id_gen")
	@SequenceGenerator(name="parrent_id_gen", allocationSize=1, sequenceName="parrent_id_seq")
	private Long id;
	//TODO: check all cascade type
	@OneToOne(fetch=FetchType.EAGER, cascade={CascadeType.ALL}, targetEntity=PersonalInfoImpl.class)
	@JoinColumn(name="personal_info")
	private PersonalInfo personalInfo;
	private String email;
	
	public static Parent newParent() {
		Parent instance = new ParentImpl();
		instance.setPersonalInfo(new PersonalInfoImpl());
		return instance;
	}
	
	
	@Override
	public Long getId() {
		return id;
	}

	
	@Override
	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public PersonalInfo getPersonalInfo() {
		return personalInfo;
	}
	
	@Override
	public void setPersonalInfo(PersonalInfo personalInfo) {
		this.personalInfo = personalInfo;
	}
	
	@Override
	public String getEmail() {
		return email;
	}
	
	@Override
	public void setEmail(String email) {
		this.email = email;
	}


	@Override
	public String toString() {
		return "Parrent [id=" + id + ", personalInfo=" + personalInfo + ", email=" + email + "]";
	}
	
	
}
