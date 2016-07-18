package nks.abc.core.exception.handler;

import nks.abc.core.exception.service.ServiceDisplayedErorr;
import nks.abc.core.exception.service.ServiceException;
import nks.abc.core.util.ExternalMessage;
import nks.abc.core.util.ExternalMessage.MessageSeverity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("webErrorHandler")
public class WebErrorHandler extends ErrorHandler {
	
	private static final String DEFAULT_ERROR_MESSAGE = "Action is not committed! Something went wrong!";

	@Autowired
	private ExternalMessage message;
	
	@Override
	public void handle(Exception e, String where) {
		if(e instanceof ServiceDisplayedErorr){
			ServiceDisplayedErorr sde = (ServiceDisplayedErorr) e;
			message.send(MessageSeverity.ERROR, "Error: " + sde.getDisplayedText());
			log.error("Catch service displayed error on " + where, sde);
		}
		else if(e instanceof ServiceException){
			message.send(MessageSeverity.ERROR, DEFAULT_ERROR_MESSAGE);
			log.error("Catch service not displayed error on " + where, e);
		}
		else {
			message.send(MessageSeverity.FATAL_ERROR, DEFAULT_ERROR_MESSAGE);
			log.error("Application crashed! Undefined exception on " + where, e);
		}
	}
}
