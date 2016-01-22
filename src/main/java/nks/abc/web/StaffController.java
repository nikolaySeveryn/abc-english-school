package nks.abc.web;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.ManagedBean;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;

import nks.abc.domain.dto.user.StaffDTO;
import nks.abc.service.StaffService;
import nks.abc.service.exception.ServiceDisplayedErorr;
import nks.abc.service.exception.ServiceException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
@ManagedBean("staffController")
@SessionScoped
public class StaffController implements Serializable {
	
	private static final long serialVersionUID = -5855175113160274311L;
	private static final Logger log = Logger.getLogger(StaffController.class);

	@Autowired
	private StaffService staffService;
	
	private Map<Long,Boolean> checked = new HashMap<Long, Boolean>();
	private StaffDTO edited = null;
	private EditMode editMode = EditMode.NONE;
	
	public enum EditMode{
		NONE,
		EDIT,
		ADD
	}
	
	public List<StaffDTO> getList() {
		try {
			return staffService.getAll();
		} catch (ServiceDisplayedErorr e) {
			addMessage(FacesMessage.SEVERITY_ERROR,
					"Error: " + e.getDisplayedText());
			log.error("error on getting staff lisst", e);
			return new ArrayList<StaffDTO>();
		} catch (ServiceException e) {
			addMessage(FacesMessage.SEVERITY_ERROR, "Error");
			log.error("error on getting staff lisst", e);
			return new ArrayList<StaffDTO>();
		}
	}

	public void delete() {
		try {
			for(Map.Entry<Long, Boolean> en : checked.entrySet()) {
				if(en.getValue()){
					staffService.delete(en.getKey(), getCurrentUsername());
				}
			}
			addMessage(FacesMessage.SEVERITY_INFO, "Deleted");
		} catch (ServiceDisplayedErorr e) {
			addMessage(FacesMessage.SEVERITY_ERROR,  "Error: " + e.getDisplayedText());
			log.error("error on deleting staff", e);
		} catch (ServiceException e) {
			addMessage(FacesMessage.SEVERITY_ERROR, "Error");
			log.error("error on deleting staff", e);
		}
	}

	public String add() {
		try {
			editMode = EditMode.ADD;
			edited = new StaffDTO();
		} catch (ServiceDisplayedErorr e) {
			addMessage(FacesMessage.SEVERITY_ERROR,  "Error: " + e.getDisplayedText());
			log.error("error on adding staff", e);
			return null;
		} catch (ServiceException e) {
			addMessage(FacesMessage.SEVERITY_ERROR, "Error");
			log.error("error on adding staff", e);
			return null;
		}
		return "staffEdit.xhtml";
	}
	
	public String edit(Long id) {
		try {
			editMode = EditMode.EDIT;
			this.edited = staffService.findById(id);
		} catch (ServiceDisplayedErorr e) {
			addMessage(FacesMessage.SEVERITY_ERROR,  "Error: " + e.getDisplayedText());
			log.error("error on editing staff", e);
			return null;
		} catch (ServiceException e) {
			addMessage(FacesMessage.SEVERITY_ERROR, "Error");
			log.error("error on editing staff", e);
			return null;
		}
		return "staffEdit.xhtml";
	}
	
	public String save() {
		String msg = new String();
		try {
			if (editMode.equals(EditMode.EDIT)) {
				staffService.update(edited, getCurrentUsername());
				msg = "Updated";
			} else if (editMode.equals(EditMode.ADD)) {
				staffService.add(edited);
				msg = "Added";
			} else {
				addMessage(FacesMessage.SEVERITY_ERROR, "Error");
				return null;
			}
		} catch (ServiceDisplayedErorr e) {
			addMessage(FacesMessage.SEVERITY_ERROR,  "Error: " + e.getDisplayedText());
			log.error("error on saving staff", e);
			return null;
		} catch (ServiceException e) {
			addMessage(FacesMessage.SEVERITY_ERROR, "Error");
			log.error("error on saving staff", e);
			return null;
		}
		
		addMessage(FacesMessage.SEVERITY_INFO, msg);
		return "staffList.xhtml";
	}
	
	/* 
	 *  privates
	 */
	private void addMessage(Severity severity, String msg) {
		FacesMessage message = new FacesMessage(severity, msg,  null);
		FacesContext.getCurrentInstance().addMessage(null, message);
	}
	
	private String getCurrentUsername() {
		return FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal().getName();
	}
	
	/*
	 * getters & setters
	 */
	
	public Map<Long, Boolean> getChecked() {
		return checked;
	}

	public void setChecked(Map<Long, Boolean> checked) {
		this.checked = checked;
	}

	public StaffDTO getEdited() {
		return edited;
	}

	public void setEdited(StaffDTO edited) {
		this.edited = edited;
	}

	public EditMode getEditMode() {
		return editMode;
	}

	public void setEditMode(EditMode editMode) {
		this.editMode = editMode;
	}
	
	public Boolean getIsNew() {
		if(getEditMode().equals(EditMode.ADD)) {
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}
	
}
