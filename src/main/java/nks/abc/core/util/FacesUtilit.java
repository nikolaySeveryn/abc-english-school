package nks.abc.core.util;

import javax.faces.application.FacesMessage.Severity;

public interface FacesUtilit {
	String getCurrentUserName();
	void addMessage(Severity severity, String msg);
}
