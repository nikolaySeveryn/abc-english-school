package nks.abc.bl.domain.user;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

import nks.abc.bl.view.validation.annotation.Email;

@Entity
public class Parrent {
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="parrent_id_gen")
	@SequenceGenerator(name="parrent_id_gen", allocationSize=1, sequenceName="parrent_id_seq")
	private Long id;
	//TODO: check all cascade type
	@OneToOne(cascade={CascadeType.ALL})
	@JoinColumn(name="personal_info")
	private PersonalInfo personalInfo;
	@Email
	private String email;
	
	
	public Long getId() {
		return id;
	}

	
	public void setId(Long id) {
		this.id = id;
	}

	public PersonalInfo getPersonalInfo() {
		return personalInfo;
	}
	
	public void setPersonalInfo(PersonalInfo personalInfo) {
		this.personalInfo = personalInfo;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
}
