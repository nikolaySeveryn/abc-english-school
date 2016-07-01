package nks.abc.core.util;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;

import org.springframework.stereotype.Component;

@Component
public class PrimefacesUtilit implements FacesUtilit {

	@Override
	public String getCurrentUserName() {
		return FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal().getName();
	}

	@Override
	public void addMessage(Severity severity, String msg) {
		FacesMessage message = new FacesMessage(severity, msg,  null);
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

}
