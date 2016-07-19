package nks.abc.core.util;

public interface UIMessage {
	
	public enum MessageSeverity {
		ERROR,
		FATAL_ERROR,
		INFO,
		WARNING
	}
	
	void send(MessageSeverity severity, String msg);
}
