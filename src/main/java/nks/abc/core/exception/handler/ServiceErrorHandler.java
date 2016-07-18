package nks.abc.core.exception.handler;

import org.springframework.stereotype.Component;

import nks.abc.core.exception.repository.ConversionException;
import nks.abc.core.exception.repository.RepositoryException;
import nks.abc.core.exception.service.ServiceException;

@Component("serviceErrorHandler")
public class ServiceErrorHandler extends ErrorHandler {

	@Override
	public void handle(Exception e, String where) {
		if(e instanceof RepositoryException){
			log.error("Database error on " + where , e);
			throw new ServiceException("Database error on " + where, e);
		}
		else if (e instanceof ConversionException){
			log.error("Error during view convertion on " + where, e);
			throw new ServiceException("Error during view convertion on " + where, e);
		}
		else {
			log.error("Unknown error on " + where, e);
			throw new ServiceException("Unknown error on " + where, e);
		}
	}

}
