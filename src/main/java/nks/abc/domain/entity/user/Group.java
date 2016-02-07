package nks.abc.domain.entity.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
public class Group {
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="group_id_gen")
	@SequenceGenerator(name="group_id_gen", allocationSize=1, sequenceName="group_id_seq")
	private Long id;
	@Column(nullable=false)
	private String name;
	@Column(nullable=false)
	@Enumerated(EnumType.STRING)
	private Level level;
	@Column(nullable=false)
	private Integer tarif;
//	private Teacher teacher;
	//TODO: plan
	
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Level getLevel() {
		return level;
	}
	public void setLevel(Level level) {
		this.level = level;
	}
	public Integer getTarif() {
		return tarif;
	}
	public void setTarif(Integer tarif) {
		this.tarif = tarif;
	}
	
	
	
}
