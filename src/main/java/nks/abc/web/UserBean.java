package nks.abc.web;

import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import java.io.Serializable;

@ManagedBean(name="userBean")
@SessionScoped
public class UserBean implements Serializable {

	private static final long serialVersionUID = 919819109293109878L;

	public void logout() {
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
	}

}
