package nks.abc.web;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;

import nks.abc.domain.dto.user.StaffDTO;
import nks.abc.service.StaffService;
import nks.abc.service.exception.ServiceDisplayedErorr;
import nks.abc.service.exception.ServiceException;
import nks.abc.web.common.enumeration.EditingMode;
import nks.abc.web.common.util.FacesUtilit;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
@ManagedBean
@SessionScoped
public class StaffBean implements Serializable {
	
	private static final long serialVersionUID = -5855175113160274311L;
	private static final Logger log = Logger.getLogger(StaffBean.class);

	@Autowired
	private StaffService staffService;
	
	@Autowired
	private UserBean userBean;
	
	@Autowired
	private FacesUtilit utilit;
	
	private Map<Long,Boolean> checked = new HashMap<Long, Boolean>();
	private StaffDTO edited = null;
	private EditingMode editMode = EditingMode.NONE;
	
	public List<StaffDTO> getList() {
		try {
			return staffService.getAll();
		} catch (ServiceDisplayedErorr e) {
			utilit.addMessage(FacesMessage.SEVERITY_ERROR,
					"Error: " + e.getDisplayedText());
			log.error("error on getting staff lisst", e);
			return new ArrayList<StaffDTO>();
		} catch (ServiceException e) {
			utilit.addMessage(FacesMessage.SEVERITY_ERROR, "Error");
			log.error("error on getting staff lisst", e);
			return new ArrayList<StaffDTO>();
		} catch (Exception e) {
			utilit.addMessage(FacesMessage.SEVERITY_ERROR, "Error");
			log.error("undifined error", e);
			return new ArrayList<StaffDTO>();
		}
	}

	public void delete() {
		try {
			for(Map.Entry<Long, Boolean> en : checked.entrySet()) {
				if(en.getValue()){
					staffService.delete(en.getKey(), userBean.getCurrentUserName());
				}
			}
			utilit.addMessage(FacesMessage.SEVERITY_INFO, "Deleted");
		} catch (ServiceDisplayedErorr e) {
			utilit.addMessage(FacesMessage.SEVERITY_ERROR,  "Error: " + e.getDisplayedText());
			log.error("error on deleting staff", e);
		} catch (ServiceException e) {
			utilit.addMessage(FacesMessage.SEVERITY_ERROR, "Error");
			log.error("error on deleting staff", e);
		} catch (Exception e) {
			utilit.addMessage(FacesMessage.SEVERITY_ERROR, "Error");
			log.error("undifined error", e);
		}
	}

	public String add() {
		try {
			editMode = EditingMode.ADD;
			edited = new StaffDTO();
		} catch (ServiceDisplayedErorr e) {
			utilit.addMessage(FacesMessage.SEVERITY_ERROR,  "Error: " + e.getDisplayedText());
			log.error("error on adding staff", e);
			return null;
		} catch (ServiceException e) {
			utilit.addMessage(FacesMessage.SEVERITY_ERROR, "Error");
			log.error("error on adding staff", e);
			return null;
		} catch (Exception e) {
			utilit.addMessage(FacesMessage.SEVERITY_ERROR, "Error");
			log.error("undifined error", e);
			return null;
		}
		return "staffEdit.xhtml";
	}
	
	public String edit(StaffDTO edited) {
		try {
			editMode = EditingMode.EDIT;
			this.edited = edited;
		} catch (ServiceDisplayedErorr e) {
			utilit.addMessage(FacesMessage.SEVERITY_ERROR,  "Error: " + e.getDisplayedText());
			log.error("error on editing staff", e);
			return null;
		} catch (ServiceException e) {
			utilit.addMessage(FacesMessage.SEVERITY_ERROR, "Error");
			log.error("error on editing staff", e);
			return null;
		} catch (Exception e) {
			utilit.addMessage(FacesMessage.SEVERITY_ERROR, "Error");
			log.error("undifined error", e);
			return null;
		}
		return "staffEdit.xhtml";
	}
	
	public String save() {
		String msg = new String();
		try {
			if (editMode.equals(EditingMode.EDIT)) {
				staffService.update(edited, userBean.getCurrentUserName());
				msg = "Updated";
			} else if (editMode.equals(EditingMode.ADD)) {
				staffService.add(edited);
				msg = "Added";
			} else {
				utilit.addMessage(FacesMessage.SEVERITY_ERROR, "Error");
				return null;
			}
		} catch (ServiceDisplayedErorr e) {
			utilit.addMessage(FacesMessage.SEVERITY_ERROR,  "Error: " + e.getDisplayedText());
			log.error("error on saving staff", e);
			return null;
		} catch (ServiceException e) {
			utilit.addMessage(FacesMessage.SEVERITY_ERROR, "Error");
			log.error("error on saving staff", e);
			return null;
		} catch (Exception e) {
			utilit.addMessage(FacesMessage.SEVERITY_ERROR, "Error");
			log.error("undifined error", e);
			return null;
		}
		
		utilit.addMessage(FacesMessage.SEVERITY_INFO, msg);
		return "staffList.xhtml";
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

	public EditingMode getEditMode() {
		return editMode;
	}

	public void setEditMode(EditingMode editMode) {
		this.editMode = editMode;
	}
	
	public Boolean getIsNew() {
		if(getEditMode().equals(EditingMode.ADD)) {
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}
	
}
