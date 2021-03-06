package nks.abc.web.common.converter;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import nks.abc.domain.dto.user.StaffDTO;
import nks.abc.service.StaffService;
import nks.abc.service.exception.ServiceException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("request")
//@FacesConverter("staffConverter")
public class StaffConverter implements Converter {
	
	private static final Logger log = Logger.getLogger(StaffConverter.class);
	
	@Autowired
	private StaffService staffService;

	@Override
	public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
		if(value == null || value.trim().length() < 1){
			return null;
		}
		try{
			return staffService.getById(Long.parseLong(value));
		}
		catch(ServiceException e){
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
	public String getAsString(FacesContext fc, UIComponent uic, Object object) {
		if(object == null) {
			return null;
		}
		if(!(object instanceof StaffDTO)) {
			throw new ConverterException("Conversion error: is not instance of staffDTO");
		}
		return ((StaffDTO)object).getAccountId().toString();
	}

}
