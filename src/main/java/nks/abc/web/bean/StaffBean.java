package nks.abc.web.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;

import nks.abc.core.exception.handler.ErrorHandler;
import nks.abc.domain.user.Account;
import nks.abc.domain.user.Administrator;
import nks.abc.domain.user.HumanResources;
import nks.abc.domain.user.Teacher;
import nks.abc.domain.user.factory.AccountFactory;
import nks.abc.domain.user.factory.UserFactory;
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
	
	private static final String LIST_PAGE = "staffList.xhtml";
	private static final String EDIT_PAGE = "staffEdit.xhtml";
	private static final long serialVersionUID = -5855175113160274311L;
	private static final Logger log = Logger.getLogger(StaffBean.class);
	private ErrorHandler errorHandler;
	
	@Autowired
	private HumanResources staffService;
	
	@Autowired
	private UserBean userBean;
	
	@Autowired
	private UIMessage message;
	
	private Map<Long,Boolean> checked = new HashMap<Long, Boolean>();
	
	private Account editedAccount = null;
	private Administrator editedAdmin = null;
	private Teacher editedTeacher = null;
	private EditingMode editMode = EditingMode.NONE;

	
	@Autowired
	@Qualifier("webErrorHandler")
	public void setErrorHandler(ErrorHandler errorHandler) {
		this.errorHandler = errorHandler;
		this.errorHandler.loggerFor(this.getClass());
	}
	
	public List<Account> getAccountsList() {
		try {
			List<Account> result = staffService.getAllStaffAccounts();
			Collections.sort(result, new Comparator<Account>() {
				@Override
				public int compare(Account left, Account right) {
					if(left.getAccountId() > right.getAccountId()){
						return -1;
					}
					if(left.getAccountId() < right.getAccountId()){
						return 1;
					}
					return 0;
				}
			});
			return result;
		} catch (Exception e) {
			errorHandler.handle(e, "getting all staff");
			return new ArrayList<Account>();
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
					staffService.fire(en.getKey(), userBean.getCurrentUserName());
				}
			}
			checked.clear();
			message.send(MessageSeverity.INFO, "Disabled");
		} catch (Exception e) {
			errorHandler.handle(e, "Fired staff");
		}
	}
	
	public void enable(){
		try {
			for(Map.Entry<Long, Boolean> en : checked.entrySet()) {
				if(en.getValue()){
					staffService.rehire(en.getKey());
				}
			}
			checked.clear();
			message.send(MessageSeverity.INFO, "Activated");
		} catch (Exception e) {
			errorHandler.handle(e, "Re hired staff");
		}
	}

	public String add() {
		try {
			editMode = EditingMode.ADD;
			editedAccount = AccountFactory.createAccount();
			editedTeacher = UserFactory.createTeacher();
			editedAdmin = UserFactory.createAdministrator();
		} catch (Exception e) {
			errorHandler.handle(e, "start adding user");
			return null;
		}
		return EDIT_PAGE;
	}
	
	public String edit(Long accountId) {
		try {
			editMode = EditingMode.EDIT;
			this.editedAccount =  staffService.getAccountById(accountId);
			this.editedTeacher = this.editedAccount.getTeacherData();
			this.editedAdmin = this.editedAccount.getAdministratorData();
			
		} catch (Exception e) {
			errorHandler.handle(e, "start editing user");
			return null;
		}
		return EDIT_PAGE;
	}
	
	public String save() {
		String msg = new String();
		try {
			if (editMode.equals(EditingMode.EDIT)) {
				staffService.update(editedAccount, editedTeacher, editedAdmin, userBean.getCurrentUserName());
				msg = "Updated";
			} else if (editMode.equals(EditingMode.ADD)) {
				staffService.add(editedAccount, editedTeacher, editedAdmin);
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
		return LIST_PAGE;
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

	public Account getEdited() {
		return editedAccount;
	}

	public void setEdited(Account edited) {
		this.editedAccount = edited;
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
