package nks.abc.web.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import nks.abc.domain.errors.ErrorsSet;
import nks.abc.web.common.message.MessageSeverity;

public abstract class ActionPerformer <E,P> {
	
	private static final Logger log = Logger.getLogger(ActionPerformer.class);
	
	private List<E> successes = new ArrayList<E>();
	private Map<E,Set<String>> errors = new HashMap<E,Set<String>>();
	private ErrorsSet<E> currenErrors;
	
	private String succesTitle = "Success:";
	private String errorTitle = "Error:";
	
	public ActionPerformer(){
		super();
	}
	
	public ActionPerformer(String succesTitle, String errorTitle) {
		super();
		this.succesTitle = succesTitle;
		this.errorTitle = errorTitle;
	}



	private void seekErrors(P param) {
		currenErrors = checkAction(param);
		if(currenErrors.hasErrors()) {
			errors.put(currenErrors.getEntity(), currenErrors.getErrors());
		}
	}
	
	public void doAction(P param) {
		seekErrors(param);
		if (! currenErrors.hasErrors()) {
			mainAction(param);
			successes.add(currenErrors.getEntity());
		}
	}
	
	public void showResults(){
		if(successes.size()>0){
			showSuccesMessages();
		}
		if(errors.size()>0){
			showErrorMessagees();
		}
	}
	
	public void clearResults(){
		successes.clear();
		errors.clear();
		currenErrors = null;
	}
	
	private void showSuccesMessages(){
		StringBuffer buffer = new StringBuffer();
		for(E entity: successes){
			if(buffer.length() != 0){
				buffer.append("; ");
			}
			buffer.append(getName(entity));
		}
		sendMessage(MessageSeverity.INFO, succesTitle, buffer.toString());
	}
	
	private void showErrorMessagees(){
		for(Entry<E,Set<String>>entityErrors: errors.entrySet()){
			StringBuffer buffer = new StringBuffer();
			for(String message: entityErrors.getValue()){
				if(buffer.length() != 0){
					buffer.append("; ");
				}
				buffer.append(message);
			}
			sendMessage(MessageSeverity.WARNING, errorTitle + " " + getName(entityErrors.getKey()), buffer.toString());
		}
	}

	protected abstract void sendMessage(MessageSeverity severity,String title, String detail);
	protected abstract ErrorsSet<E> checkAction(P param);
	protected abstract void mainAction(P param);
	protected abstract String getName(E entity);
}
