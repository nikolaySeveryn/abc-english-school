package nks.abc.web.common;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.springframework.stereotype.Component;

import nks.abc.core.util.UIMessage;

@Component
public class PFMessage implements UIMessage {

	@Override
	public void send(MessageSeverity severity, String msg) {
		
		FacesMessage message = new FacesMessage(toPrimeSeverity(severity), msg,  null);
		FacesContext.getCurrentInstance().addMessage(null, message);
	}
	
	private FacesMessage.Severity toPrimeSeverity(MessageSeverity severity){
		switch (severity) {
			case ERROR:
				return FacesMessage.SEVERITY_ERROR;
			case FATAL_ERROR:
				return FacesMessage.SEVERITY_FATAL;
			case INFO:
				return FacesMessage.SEVERITY_INFO;
			case WARNING:
				return FacesMessage.SEVERITY_WARN;
			default:
				throw new IllegalArgumentException("Unknown message severity"); 
		}
		
	}

}
