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

import nks.abc.domain.errors.ErrorsSet;
import nks.abc.domain.user.Account;
import nks.abc.domain.user.Administrator;
import nks.abc.domain.user.HumanResources;
import nks.abc.domain.user.Staff;
import nks.abc.domain.user.Teacher;
import nks.abc.domain.user.User;
import nks.abc.domain.user.factory.AccountFactory;
import nks.abc.domain.user.factory.UserFactory;
import nks.abc.web.common.ActionPerformer;
import nks.abc.web.common.enumeration.BeanState;
import nks.abc.web.common.message.MessageSeverity;
import nks.abc.web.common.message.UIMessage;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@ManagedBean
@SessionScoped
public class StaffBean implements Serializable {

	private static final long serialVersionUID = 1742023794919379167L;
	
	private static final String LIST_PAGE = "staffList.xhtml";
	private static final String EDIT_PAGE = "staffEdit.xhtml";
	private static final Logger log = Logger.getLogger(StaffBean.class);

	@Autowired
	private HumanResources hr;
	@Autowired
	private SessionBean userBean;
	@Autowired
	private UIMessage uiAlert;

	private Map<Long,Boolean> checked = new HashMap<Long,Boolean>();
	private Account editedAccount = null;
	private Administrator editedAdmin = null;
	private Teacher editedTeacher = null;
	private BeanState state = BeanState.LIST;
	private StaffRole role;

	public enum StaffRole {
		TEACHER, ADMINISTRATOR
	}

	public List<Account> getAccountsList() {
		try {
			List<Account> result = hr.getAllStaffAccounts();
			Collections.sort(result, new Comparator<Account>() {

				@Override
				public int compare(Account left, Account right) {
					if (left.getAccountId() > right.getAccountId()) {
						return -1;
					}
					if (left.getAccountId() < right.getAccountId()) {
						return 1;
					}
					return 0;
				}
			});
			return result;
		}
		catch (Exception e) {
			log.error("error on getting account list", e);
			uiAlert.sendError();
			return new ArrayList<Account>();
		}
	}

	public void delete() {

		ActionPerformer<Staff,Long> remover = new ActionPerformer<Staff,Long>("Deleted", "Can't delete") {
			@Override
			protected ErrorsSet<Staff> checkAction(Long id) {
				return hr.checkDeletePosibility(id, userBean.getCurrentUserName());
			}
			@Override
			protected void mainAction(Long id) {
				hr.deleteStaff(id, userBean.getCurrentUserName());
			}
			@Override
			protected String getName(Staff entity) {
				return entity.getFullName();
			}
			@Override
			protected void sendMessage(MessageSeverity severity, String title, String detail) {
				uiAlert.send(severity, title, detail);
			}
		};
		try {
			for (Map.Entry<Long,Boolean> en : checked.entrySet()) {
				if (en.getValue()) {
					remover.doAction(en.getKey());
				}
			}
		}
		catch (Exception e) {
			log.error("error on staff deleting");
			uiAlert.sendError();
		}
		finally {
			remover.showResults();
			checked.clear();
		}
	}

	public void fire() {
		ActionPerformer<Staff,Long> disabler = new ActionPerformer<Staff,Long>("Fired", "Can't fire") {
			@Override
			protected ErrorsSet<Staff> checkAction(Long id) {
				return hr.checkFirePosibility(id, userBean.getCurrentUserName());
			}
			@Override
			protected void mainAction(Long id) {
				hr.fire(id, userBean.getCurrentUserName());
			}
			@Override
			protected String getName(Staff entity) {
				return entity.getFullName();
			}
			@Override
			protected void sendMessage(MessageSeverity severity, String title, String detail) {
				uiAlert.send(severity, title, detail);
			}
		};
		try {
			for (Map.Entry<Long,Boolean> en : checked.entrySet()) {
				if (en.getValue()) {
					disabler.doAction(en.getKey());
				}
			}
		}
		catch (Exception e) {
			log.error("error on firing staff");
			uiAlert.sendError();
		}
		finally {
			disabler.showResults();
			checked.clear();
		}
	}

	public void rehire() {
		try {
			for (Map.Entry<Long,Boolean> en : checked.entrySet()) {
				if (en.getValue()) {
					hr.rehire(en.getKey());
				}
			}
			uiAlert.send(MessageSeverity.INFO, "Activated");
		}
		catch (Exception e) {
			log.error("error on staff rehireing");
			uiAlert.sendError();
		}
		finally {
			checked.clear();
		}
	}

	public String add() {
		state = BeanState.ADD;
		editedAccount = AccountFactory.createAccount();
		editedTeacher = UserFactory.createTeacher();
		editedAdmin = UserFactory.createAdministrator();
		return EDIT_PAGE;
	}

	public String edit(Long accountId) {
		try {
			state = BeanState.EDIT;
			this.editedAccount = hr.getAccountById(accountId);
			User user = this.editedAccount.getUser();
			if (user.isAdministrator()) {
				this.role = StaffRole.ADMINISTRATOR;
				this.editedAdmin = (Administrator) user;
			}
			else if (user.isTeacher()) {
				this.role = StaffRole.TEACHER;
				this.editedTeacher = (Teacher) user;
			}
		}
		catch (Exception e) {
			log.error("error on trying edit staff account id:" + accountId, e);
			uiAlert.sendError();
			state = BeanState.LIST;
			return null;
		}
		return EDIT_PAGE;
	}

	public String save() {
		String msg = new String();
		try {
			System.out.println("Role : " + role);
			if (role.equals(StaffRole.ADMINISTRATOR)) {
				msg = saveAdmin();
			}
			else if (role.equals(StaffRole.TEACHER)) {
				msg = saveTeacher();
			}
			else {
				log.error("Error on saving staff: role");
				uiAlert.sendError();
				return null;
			}
		}
		catch (Exception e) {
			log.error("error on staff saving");
			uiAlert.sendError();
			return null;
		}
		finally {
			state = BeanState.LIST;
			role = null;
		}

		uiAlert.send(MessageSeverity.INFO, msg);
		return LIST_PAGE;
	}
	//TODO: refactor this shit
	private String saveTeacher() {
		String msg = null;
		if (state.equals(BeanState.EDIT)) {
			editedTeacher.setAccount(editedAccount);
			hr.updateStaff(editedTeacher, userBean.getCurrentUserName());
			msg = "Updated";
		}
		else if (state.equals(BeanState.ADD)) {
			editedTeacher.setAccount(editedAccount);
			hr.hireTeacher(editedTeacher);
			msg = "Added";
		}
		return msg;
	}

	private String saveAdmin() {
		String msg = null;
		if (state.equals(BeanState.EDIT)) {
			editedAdmin.setAccount(editedAccount);
			hr.updateStaff(editedAdmin, userBean.getCurrentUserName());
			msg = "Updated";
		}
		else if (state.equals(BeanState.ADD)) {
			editedAdmin.setAccount(editedAccount);
			hr.hireAdministrator(editedAdmin);
			msg = "Added";
		}
		return msg;
	}

	/*
	 * getters & setters
	 */

	public Map<Long,Boolean> getChecked() {
		return checked;
	}

	public void setChecked(Map<Long,Boolean> checked) {
		this.checked = checked;
	}

	public Account getEdited() {
		return editedAccount;
	}

	public void setEdited(Account edited) {
		this.editedAccount = edited;
	}

	public BeanState getEditMode() {
		return state;
	}

	public StaffRole getRole() {
		return role;
	}

	public void setRole(StaffRole role) {
		this.role = role;
	}

	public void setEditMode(BeanState editMode) {
		this.state = editMode;
	}

	public Boolean getIsNew() {
		if (getEditMode().equals(BeanState.ADD)) {
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

	public StaffRole[] getRoles() {
		return StaffRole.values();
	}

}
