package nks.abc.web.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;

import nks.abc.core.exception.handler.ErrorHandler;
import nks.abc.core.exception.service.ServiceDisplayedErorr;
import nks.abc.core.exception.service.ServiceException;
import nks.abc.depricated.service.user.StaffService;
import nks.abc.depricated.view.factory.UserViewFactory;
import nks.abc.depricated.view.object.objects.user.StaffView;
import nks.abc.web.common.enumeration.EditingMode;
import nks.abc.web.common.message.MessageSeverity;
import nks.abc.web.common.message.UIMessage;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;


@Component
@ManagedBean
@SessionScoped
public class StaffBean implements Serializable {
	
	private static final long serialVersionUID = -5855175113160274311L;
	private static final Logger log = Logger.getLogger(StaffBean.class);
	private ErrorHandler errorHandler;
	
	@Autowired
	private StaffService staffService;
	
	@Autowired
	private UserBean userBean;
	
	@Autowired
	private UIMessage message;
	
	private Map<Long,Boolean> checked = new HashMap<Long, Boolean>();
	private StaffView edited = null;
	private EditingMode editMode = EditingMode.NONE;
	
	@Autowired
	@Qualifier("webErrorHandler")
	public void setErrorHandler(ErrorHandler errorHandler) {
		this.errorHandler = errorHandler;
		this.errorHandler.loggerFor(this.getClass());
	}
	
	public List<StaffView> getList() {
		try {
			return staffService.getAllStaff();
		} catch (Exception e) {
			errorHandler.handle(e, "getting all staff");
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
			checked.clear();
			message.send(MessageSeverity.INFO, "Deleted");
		} catch (Exception e) {
			errorHandler.handle(e, "delete users");
		}
	}
	
	public void disable(){
		try {
			for(Map.Entry<Long, Boolean> en : checked.entrySet()) {
				if(en.getValue()){
					staffService.disable(en.getKey(), userBean.getCurrentUserName());
				}
			}
			checked.clear();
			message.send(MessageSeverity.INFO, "Disabled");
		} catch (Exception e) {
			errorHandler.handle(e, "disable users");
		}
	}
	
	public void enable(){
		try {
			for(Map.Entry<Long, Boolean> en : checked.entrySet()) {
				if(en.getValue()){
					staffService.enable(en.getKey());
				}
			}
			checked.clear();
			message.send(MessageSeverity.INFO, "Activated");
		} catch (Exception e) {
			errorHandler.handle(e, "enable users");
		}
	}

	public String add() {
		try {
			editMode = EditingMode.ADD;
			edited = UserViewFactory.newStaff();
		} catch (Exception e) {
			errorHandler.handle(e, "start adding user");
			return null;
		}
		return "staffEdit.xhtml";
	}
	
	public String edit(StaffView edited) {
		try {
			editMode = EditingMode.EDIT;
			this.edited = edited;
		} catch (Exception e) {
			errorHandler.handle(e, "start editing user");
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
				message.send(MessageSeverity.ERROR, "Error");
				return null;
			}
		} catch (Exception e) {
			errorHandler.handle(e, "save user");
			return null;
		}
		
		message.send(MessageSeverity.INFO, msg);
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
