package nks.abc.depricated.view.object.objects.user;

import javax.annotation.ManagedBean;

@ManagedBean
public class StaffView extends UserView{
	
	private Boolean isTeacher;
	private Boolean isAdministrator;

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
		return "StaffDTO [isTeacher="
				+ isTeacher + ", isAdministrator=" + isAdministrator
				+ ", super.toString()=" + super.toString() + "]";
	}

	
}
