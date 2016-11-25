package nks.abc.web.bean;

import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import nks.abc.domain.user.HumanResources;
import nks.abc.domain.user.User;
import nks.abc.domain.user.factory.UserFactory;

import java.io.Serializable;

@Component
@ManagedBean
@SessionScoped
public class SessionBean implements Serializable {
	
	private static final Logger log = Logger.getLogger(SessionBean.class);
	
	@Autowired
	private HumanResources hr;

	private static final long serialVersionUID = 919819109293109878L;

	public void logout() {
		getExternalContext().invalidateSession();
	}

	public User getCurrentUser() {
		try{
			return hr.getActiveStaffByEmail(getCurrentUserName());
		}
		catch(Exception e){
			log.error("error on getting current user, username:" + getCurrentUserName());
			return null;
		}
//		User user = UserFactory.createAdministrator();
//		user.getAccount().setEmail("nkstestemail1@gmail.com");
//		user.getPersonalInfo().setSirName("Test");
//		user.getPersonalInfo().setFirstName("User");
//		return user;
	}

	private ExternalContext getExternalContext() {
		return FacesContext.getCurrentInstance().getExternalContext();
	}
	
	
	public String getCurrentUserName() {
		return getExternalContext().getUserPrincipal().getName();
//		return "nkstestemail1@gmail.com";
	}
}