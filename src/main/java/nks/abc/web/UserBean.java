package nks.abc.web;

import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import nks.abc.domain.view.object.objects.user.StaffView;
import nks.abc.service.StaffService;

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
		System.out.println("get current user");
		System.out.println("Username= " + getCurrentUserName());
		return userService.getStaffByLogin(getCurrentUserName());
	}

	private ExternalContext getExternalContext() {
		return FacesContext.getCurrentInstance().getExternalContext();
	}
	
	public String getCurrentUserName() {
		return getExternalContext().getUserPrincipal().getName();
//		return "root";
	}

}