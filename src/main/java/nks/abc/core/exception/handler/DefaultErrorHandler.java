package nks.abc.core.exception.handler;

import java.util.ArrayList;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;

import nks.abc.core.exception.service.ServiceDisplayedErorr;
import nks.abc.core.exception.service.ServiceException;
import nks.abc.core.util.ExternalMessage;
import nks.abc.core.util.ExternalMessage.MessageSeverity;
import nks.abc.domain.view.object.objects.user.StaffView;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DefaultErrorHandler implements ErrorHandler {
	
	private static final MessageSeverity SEVERITY_ERROR = MessageSeverity.ERROR;

	@Autowired
	private ExternalMessage utilit;
	
	private Logger log = getDefaultLogger();
	
	private static Logger getDefaultLogger(){
		return Logger.getLogger(DefaultErrorHandler.class);
	}
	
	public void loggerFor(Class<?> clazz){
		this.log = Logger.getLogger(clazz);
	}
	
	@Override
	public void handle(Exception e) {
		if(e instanceof ServiceDisplayedErorr){
			ServiceDisplayedErorr sde = (ServiceDisplayedErorr) e;
			utilit.send(SEVERITY_ERROR, "Error: " + sde.getDisplayedText());
			log.error("Catch service displayed error", sde);
		}
		else if(e instanceof ServiceException){
			utilit.send(SEVERITY_ERROR, "Error");
			log.error("Catch service not displayed error", e);
		}
		else {
			utilit.send(SEVERITY_ERROR, "Error");
			log.error("Catch undefined exception", e);
		}
	}

}
