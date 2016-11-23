package nks.abc.web.common.message;

public interface UIMessage {
	
	void send(MessageSeverity severity, String msg);
	void send(MessageSeverity severity, String msg, String detail);
	void sendError();
}
