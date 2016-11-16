package nks.abc.web.bean;

import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import nks.abc.depricated.service.user.StaffService;
import nks.abc.domain.user.User;
import nks.abc.domain.user.factory.UserFactory;
import nks.abc.domain.user.impl.AdministratorImpl;

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

	public User getCurrentUser() {
//		return userService.getStaffByEmail(getCurrentUserName());
		User user = UserFactory.createAdministrator();
		user.getAccount().setEmail("nkstestemail1@gmail.com");
		user.getPersonalInfo().setSirName("Test");
		user.getPersonalInfo().setFirstName("User");
		user.getAccount().setIsAdministrator(true);
		user.getAccount().setIsTeacher(true);
		return user;
	}

	private ExternalContext getExternalContext() {
		return FacesContext.getCurrentInstance().getExternalContext();
	}
	
	public String getCurrentUserName() {
//		return getExternalContext().getUserPrincipal().getName();
		return "nkstestemail1@gmail.com";
	}

}