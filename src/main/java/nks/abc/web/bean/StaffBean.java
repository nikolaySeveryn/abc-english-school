package nks.abc.web.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;

import nks.abc.core.exception.service.ServiceDisplayedErorr;
import nks.abc.core.exception.service.ServiceException;
import nks.abc.core.util.ExternalMessage;
import nks.abc.core.util.ExternalMessage.MessageSeverity;
import nks.abc.domain.view.factory.UserViewFactory;
import nks.abc.domain.view.object.objects.user.StaffView;
import nks.abc.service.StaffService;
import nks.abc.web.common.enumeration.EditingMode;

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
	private ExternalMessage utilit;
	
	private Map<Long,Boolean> checked = new HashMap<Long, Boolean>();
	private StaffView edited = null;
	private EditingMode editMode = EditingMode.NONE;
	
	public List<StaffView> getList() {
		try {
			return staffService.getAll();
		} catch (ServiceDisplayedErorr e) {
			utilit.send(MessageSeverity.ERROR,
					"Error: " + e.getDisplayedText());
			log.error("error on getting staff lisst", e);
			return new ArrayList<StaffView>();
		} catch (ServiceException e) {
			utilit.send(MessageSeverity.ERROR, "Error");
			log.error("error on getting staff lisst", e);
			return new ArrayList<StaffView>();
		} catch (Exception e) {
			utilit.send(MessageSeverity.ERROR, "Error");
			log.error("undifined error", e);
			return new ArrayList<StaffView>();
		}
	}

	public void delete() {
		try {
			for(Map.Entry<Long, Boolean> en : checked.entrySet()) {
				if(en.getValue()){
					staffService.delete(en.getKey(), userBean.getCurrentUserName());
				}
			}
			utilit.send(MessageSeverity.INFO, "Deleted");
		} catch (ServiceDisplayedErorr e) {
			utilit.send(MessageSeverity.ERROR,  "Error: " + e.getDisplayedText());
			log.error("error on deleting staff", e);
		} catch (ServiceException e) {
			utilit.send(MessageSeverity.ERROR, "Error");
			log.error("error on deleting staff", e);
		} catch (Exception e) {
			utilit.send(MessageSeverity.ERROR, "Error");
			log.error("undifined error", e);
		}
	}

	public String add() {
		try {
			editMode = EditingMode.ADD;
			edited = UserViewFactory.newStaff();
		} catch (ServiceDisplayedErorr e) {
			utilit.send(MessageSeverity.ERROR,  "Error: " + e.getDisplayedText());
			log.error("error on adding staff", e);
			return null;
		} catch (ServiceException e) {
			utilit.send(MessageSeverity.ERROR, "Error");
			log.error("error on adding staff", e);
			return null;
		} catch (Exception e) {
			utilit.send(MessageSeverity.ERROR, "Error");
			log.error("undifined error", e);
			return null;
		}
		return "staffEdit.xhtml";
	}
	
	public String edit(StaffView edited) {
		try {
			editMode = EditingMode.EDIT;
			this.edited = edited;
		} catch (ServiceDisplayedErorr e) {
			utilit.send(MessageSeverity.ERROR,  "Error: " + e.getDisplayedText());
			log.error("error on editing staff", e);
			return null;
		} catch (ServiceException e) {
			utilit.send(MessageSeverity.ERROR, "Error");
			log.error("error on editing staff", e);
			return null;
		} catch (Exception e) {
			utilit.send(MessageSeverity.ERROR, "Error");
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
				utilit.send(MessageSeverity.ERROR, "Error");
				return null;
			}
		} catch (ServiceDisplayedErorr e) {
			utilit.send(MessageSeverity.ERROR,  "Error: " + e.getDisplayedText());
			log.error("error on saving staff", e);
			return null;
		} catch (ServiceException e) {
			utilit.send(MessageSeverity.ERROR, "Error");
			log.error("error on saving staff", e);
			return null;
		} catch (Exception e) {
			utilit.send(MessageSeverity.ERROR, "Error");
			log.error("undifined error", e);
			return null;
		}
		
		utilit.send(MessageSeverity.INFO, msg);
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

	public StaffView getEdited() {
		return edited;
	}

	public void setEdited(StaffView edited) {
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
