package nks.abc.web.common.message;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.springframework.stereotype.Component;

@Component
public class PFMessage implements UIMessage {

	@Override
	public void send(MessageSeverity severity, String msg) {
		FacesMessage message = new FacesMessage(toPrimeSeverity(severity), msg,  null);
		FacesContext.getCurrentInstance().addMessage(null, message);
	}
	
	@Override
	public void send(MessageSeverity severity, String msg, String detail) {
		FacesMessage message = new FacesMessage(toPrimeSeverity(severity), msg,  detail);
		FacesContext.getCurrentInstance().addMessage(null, message);
	}
	
	private FacesMessage.Severity toPrimeSeverity(MessageSeverity severity){
		
		if(severity.equals(MessageSeverity.ERROR)){
			return FacesMessage.SEVERITY_ERROR;
		}
		if(severity.equals(MessageSeverity.FATAL_ERROR)){
			return FacesMessage.SEVERITY_FATAL; 
		}
		if(severity.equals(MessageSeverity.INFO)){
			return FacesMessage.SEVERITY_INFO;
		}
		if(severity.equals(MessageSeverity.WARNING)){
			return FacesMessage.SEVERITY_WARN;
		}
		
		throw new IllegalArgumentException("Unknown message severity");
	}
}
