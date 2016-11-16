package nks.abc.web.common.validation;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import nks.abc.dao.base.UniqueChecker;

@FacesValidator("uniqueColumnValidator")
@Component
public class UniqueColumnValidator implements Validator, Serializable {
	
	private static final long serialVersionUID = -8313276241244798763L;
	private static final Logger log = Logger.getLogger(UniqueColumnValidator.class);
	
	private static ApplicationContext springContext;
	private UniqueChecker uniqueChecker;
	
	@Autowired
	public void setApplicationContext(ApplicationContext context) {
		UniqueColumnValidator.springContext = context;
	}

	@Override
	public void validate(FacesContext context, UIComponent ui, Object value) throws ValidatorException {
		synchronized (this) {
			Object entity = ui.getAttributes().get("entity");
			String field = (String) ui.getAttributes().get("field");
			String idField = (String) ui.getAttributes().get("idField");
			if(entity == null){
				log.error("Error during validation of uniqueness: entity is null");
				throw new IllegalArgumentException("Error during validation of uniqueness: entity is null");
			}
			if(field == null || field.length() < 1){
				log.error("Error during validation of uniqueness: filed name is empty");
				throw new IllegalArgumentException("Error during validation of uniqueness: filed name is empty");
			}
			if(value == null){
				// empty value is valid
				return ;
			}
			try{
				uniqueChecker = (UniqueChecker) springContext.getBean(UniqueChecker.class);
				Object id = null;
				if(idField != null) {
					id = getFieldValue(entity, idField);
				}
				boolean isUnique = uniqueChecker.isUnique(entity.getClass(), field, value, idField, id);
				
				if(! isUnique) {
					FacesMessage msg  =  new FacesMessage(FacesMessage.SEVERITY_ERROR, field + " already exist", null);
					context.addMessage(ui.getClientId(context), msg);
					throw new ValidatorException(msg);
				}
			}
			catch (ValidatorException ve){
				throw ve;
			}
			catch (Exception e) {
				String msg = "Error during validation of uniqueness. Value=" + value + ";field=" + field + ";entity= " + entity;
				log.error(msg);
				throw new RuntimeException(msg, e);
			}
		}
	}
	
	private Object getFieldValue(Object entity, String field) throws IntrospectionException, IllegalAccessException, 
			IllegalArgumentException, InvocationTargetException{
		Class<?> type = entity.getClass();
		Method getter = new PropertyDescriptor(field, type).getReadMethod();
		return getter.invoke(entity);
	}

}
