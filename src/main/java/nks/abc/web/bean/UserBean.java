package nks.abc.web.bean;

import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import nks.abc.depricated.service.user.StaffService;
import nks.abc.depricated.view.object.objects.user.StaffView;

import java.io.Serializable;

@Component
@ManagedBean
@SessionScoped
public class UserBean implements Serializable {
	
	@Autowired
	StaffService userService;

	private static final long serialVersionUID = 919819109293109878L;

	public void logout() {
		getExternalContext().invalidateSession();
	}

	public StaffView getCurrentUser() {
		return userService.getStaffByEmail(getCurrentUserName());
//		StaffView staff = new StaffView();
//		staff.setEmail("root");
//		staff.setIsAdministrator(true);
//		staff.setIsTeacher(true);
//		return staff;
	}

	private ExternalContext getExternalContext() {
		return FacesContext.getCurrentInstance().getExternalContext();
	}
	
	public String getCurrentUserName() {
		return getExternalContext().getUserPrincipal().getName();
//		return "root";
	}

}