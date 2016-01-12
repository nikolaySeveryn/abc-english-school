package nks.abc.web;

import java.io.Serializable;
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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
@ManagedBean("staffController")
@SessionScoped
public class StaffController implements Serializable{
	
	private static final long serialVersionUID = -5855175113160274311L;

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
		return staffService.getAll();
	}
	
	public void delete(){
		System.out.println("delete");
		for(Map.Entry<Long, Boolean> en : checked.entrySet()){
			System.out.println("\t" + en.getKey() + " - " + en.getValue());
		}
	}
	
	public String add() {
		editMode = EditMode.ADD;
		edited = new StaffDTO();
		return "staffEdit.xhtml?faces-redirect=true";
	}
	
	public String edit(Long id){
		editMode = EditMode.EDIT;
		this.edited = staffService.findById(id);
		System.out.println("edited = " + edited);
		return "staffEdit.xhtml?faces-redirect=true";
	}
	
	public String save(){
		String msg = new String();
		System.out.println("save staff: editMode:" + editMode + ";  edited:" + edited);
		if(editMode.equals(EditMode.EDIT)){
			staffService.update(edited);
			msg = "Додано";
		}
		else if(editMode.equals(EditMode.ADD)){
			staffService.insert(edited);
			msg = "Оновлено";
		}
		else {
			addMessage(FacesMessage.SEVERITY_ERROR,msg);
			return "staffList.xhtml?faces-redirect=true";
		}
		addMessage(FacesMessage.SEVERITY_INFO, "Помилка");
		return "staffList.xhtml?faces-redirect=true";
	}

	private void addMessage(Severity severity, String msg) {
		FacesMessage message = new FacesMessage(severity, msg,  null);
		FacesContext.getCurrentInstance().addMessage(null, message);
	}
	
	
	
	public Map<Long, Boolean> getChecked() {
		return checked;
	}

	public void setChecked(Map<Long, Boolean> checked) {
		this.checked = checked;
	}

	public StaffDTO getEdited() {
		System.out.println("getEdited: " + edited);
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
	
	public Boolean getIsNew(){
		if(getEditMode().equals(EditMode.ADD)){
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}
	
}
