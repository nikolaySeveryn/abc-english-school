package nks.abc.domain.errors;

import java.util.HashSet;
import java.util.Set;


public class ErrorsSet<T> {
	private T entity;
	private Set<String> errors = new HashSet<String>();
	
	public ErrorsSet(T entity){
		this.entity = entity;
	}
	
	public void addError(String message) {
		errors.add(message);
	}
	
	public void clear(){
		errors.clear();
	}
	
	public Boolean hasErrors() {
		return ! errors.isEmpty();
	}
	
	public Set<String> getErrors(){
		return errors;
	}
	
	public T getEntity(){
		return entity;
	}
	
	@Override
	public String toString(){
		if(!hasErrors()){
			return "No errors";
		}
		StringBuilder builder = new StringBuilder();
		builder.append("Errors for [" + entity + "]:\n");
		for(String message: errors){
			builder.append(message).append(";\n");
		}
		return builder.toString();
	}
}
