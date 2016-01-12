package nks.abc.domain.dto.user;

import javax.annotation.ManagedBean;

@ManagedBean
public class StaffDTO extends UserDTO{
	
	private String patronomic;
	private Boolean isTeacher;
	private Boolean isAdministrator;

	public String getPatronomic() {
		return patronomic;
	}
	public void setPatronomic(String patronomic) {
		this.patronomic = patronomic;
	}
	
	public Boolean getIsTeacher() {
		return isTeacher;
	}
	public void setIsTeacher(Boolean isTeacher) {
		this.isTeacher = isTeacher;
	}
	public Boolean getIsAdministrator() {
		return isAdministrator;
	}
	public void setIsAdministrator(Boolean isAdministrator) {
		this.isAdministrator = isAdministrator;
	}
	@Override
	public String toString() {
		return "StaffDTO [patronomic=" + patronomic + ", UserDTO="
				+ super.toString() + "]";
	}
	
}
