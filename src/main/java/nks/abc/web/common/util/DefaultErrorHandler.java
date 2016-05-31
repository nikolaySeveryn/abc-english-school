package nks.abc.web.common.util;

import java.util.ArrayList;

import javax.faces.application.FacesMessage;

import nks.abc.domain.view.object.objects.user.StaffView;
import nks.abc.service.exception.ServiceDisplayedErorr;
import nks.abc.service.exception.ServiceException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DefaultErrorHandler implements ErrorHandler {
	
	@Autowired
	private FacesUtilit utilit;
	
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
			utilit.addMessage(FacesMessage.SEVERITY_ERROR, "Error: " + sde.getDisplayedText());
			log.error("Catch service displayed error", sde);
		}
		else if(e instanceof ServiceException){
			utilit.addMessage(FacesMessage.SEVERITY_ERROR, "Error");
			log.error("Catch service not displayed error", e);
		}
		else {
			utilit.addMessage(FacesMessage.SEVERITY_ERROR, "Error");
			log.error("Catch undefined exception", e);
		}
	}

}
