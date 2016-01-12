package nks.abc.domain.entity.user;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(name="staffId", referencedColumnName="userId")
public class Staff extends User {
	
	public Staff(){
		super();
	}
	
	public Staff(Staff other){
		super(other);
		this.setPatronomic(other.getPatronomic());
	}
	
	private String patronomic;
	
	public String getPatronomic() {
		return patronomic;
	}
	public void setPatronomic(String patronomic) {
		this.patronomic = patronomic;
	}
	
	public void isTeacher(Boolean isTeacher){
		if(isTeacher){
			getRole().add(Role.TEACHER);
		}
		else {
			getRole().remove(Role.TEACHER);
		}
	}
	
	public Boolean isTeacher(){
		return getRole().contains(Role.TEACHER);
	}
	
	public void isAdministrator (Boolean isAdministrator){
		if(isAdministrator){
			getRole().add(Role.ADMINISTRATOR);
		}
		else if(getRole().contains(Role.ADMINISTRATOR)){
			getRole().remove(Role.ADMINISTRATOR);
		}
	}
	
	public Boolean isAdministrator(){
		return getRole().contains(Role.ADMINISTRATOR);
	}

	@Override
	public String toString() {
		return "Staff [patronomic=" + patronomic + ", super="
				+ super.toString() + "]";
	}
	
	
}
