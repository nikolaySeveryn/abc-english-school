package nks.abc.web.common.util;

public interface ErrorHandler {
	void handle(Exception e);
	void loggerFor(Class<?>clazz);
}
