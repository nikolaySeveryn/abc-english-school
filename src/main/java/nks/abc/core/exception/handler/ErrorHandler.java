package nks.abc.core.exception.handler;

import org.apache.log4j.Logger;

public abstract class ErrorHandler {
	
	protected Logger log = getDefaultLogger();
	
	public abstract void handle(Exception e, String where);
	
	public void handle(Exception e){
		handle(e, "?");
	}
	
	private static Logger getDefaultLogger() {
		return Logger.getLogger(WebErrorHandler.class);
	}
	
	public void loggerFor(Class<?> clazz) {
		this.log = Logger.getLogger(clazz);
	}
}
