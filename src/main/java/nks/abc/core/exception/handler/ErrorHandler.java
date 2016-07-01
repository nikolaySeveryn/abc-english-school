package nks.abc.core.exception.handler;

public interface ErrorHandler {
	void handle(Exception e);
	void loggerFor(Class<?>clazz);
}
