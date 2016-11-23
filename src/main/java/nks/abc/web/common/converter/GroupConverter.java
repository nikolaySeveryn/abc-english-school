package nks.abc.web.common.converter;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

import nks.abc.domain.exception.DomainException;
import nks.abc.domain.school.Group;
import nks.abc.domain.school.School;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("request")
public class GroupConverter implements Converter{
	
	private static final Logger log = Logger.getLogger(GroupConverter.class); 
	
	@Autowired
	private School groupSerice;

	@Override
	public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
		if(value == null || value.trim().length() < 1){
			return null;
		}
		try{
			return groupSerice.findGroupById(Long.parseLong(value));
		}
		catch(DomainException e){
			log.warn("Staff dto conversion error", e);
			throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Staff conversion error", "Not valid employee"));
		}
		catch (NumberFormatException e) {
			log.warn("Error of parsing staff id on conversion", e);
			throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Staff conversion error", "Not valid employee id"));
		}
		catch (Exception e){
			log.warn("Undefined error on conversion", e);
			throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Staff conversion error", "Undefined error"));
		}
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object object) {
		if(object == null){
			return null;
		}
		if(!(object instanceof Group)){
			throw new ConverterException("Conversion error: is not instance of Group");
		}
		return ((Group) object).getId().toString();
	}


}
