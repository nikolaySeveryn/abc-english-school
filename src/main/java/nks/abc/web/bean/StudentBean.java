package nks.abc.web.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;

import nks.abc.domain.errors.ErrorsSet;
import nks.abc.domain.school.Group;
import nks.abc.domain.school.Level;
import nks.abc.domain.school.School;
import nks.abc.domain.school.factory.GroupFactory;
import nks.abc.domain.user.HumanResources;
import nks.abc.domain.user.Student;
import nks.abc.domain.user.Teacher;
import nks.abc.domain.user.factory.UserFactory;
import nks.abc.web.common.ActionPerformer;
import nks.abc.web.common.enumeration.BeanState;
import nks.abc.web.common.message.MessageSeverity;
import nks.abc.web.common.message.UIMessage;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
@ManagedBean
@SessionScoped
public class StudentBean implements Serializable {

	private static final long serialVersionUID = 5963398977511644285L;
	
	private static final String GROUP_EDIT_PAGE = "groupEdit.xhtml";
	private static final String MAIN_PAGE = "main.xhtml?faces-redirect=true";
	private static final String STUDENT_EDIT_PAGE = "studentEdit.xhtml";

	private static final Logger log = Logger.getLogger(StudentBean.class);

	@Autowired
	private UIMessage uiAlert;
	
	@Autowired
	private School school;
	@Autowired
	private HumanResources staffService;

	private Map<Long,Boolean> checkedGroups = new HashMap<Long,Boolean>();
	private Map<Long,Boolean> checkedStudents = new HashMap<Long,Boolean>();
	private Group viewedGroup;
	private Group editedGroup;
	private Student editedStudent;
	private BeanState groupState = BeanState.LIST;
	private BeanState studentState = BeanState.LIST;


	public List<Group> getGroupList() {
		List<Group>groups = new ArrayList<Group>();
		try {
			groups = school.getAllGroups();
		}
		catch (Exception e) {
			log.error("error on getting all groups");
			uiAlert.sendError();
		}
		return groups;
	}

	public String addGroup() {
		editedGroup = GroupFactory.createGroup();
		groupState = BeanState.ADD;
		return GROUP_EDIT_PAGE;
	}

	public String addStudent() {
		editedStudent = UserFactory.createStudent();
		if (viewedGroup != null) {
//			Set<Group> groups = editedStudent.getGroups();
//			groups.add(viewedGroup);
//			editedStudent.setGroups(groups);
			editedStudent.getGroups().add(viewedGroup);
		}
		studentState = BeanState.ADD;
		return STUDENT_EDIT_PAGE;
	}

	public void viewGroup(Group group) {
		this.viewedGroup = group;
		checkedStudents.clear();
	}

	public String editGroup(Group group) {
		editedGroup = group;
		groupState = BeanState.EDIT;
		return GROUP_EDIT_PAGE;
	}

	public String editStudent(Long studentId) {
		editedStudent = school.findStudentById(studentId);
		studentState = BeanState.EDIT;
		return STUDENT_EDIT_PAGE;
	}

	public String saveGroup() {
		try {
			school.saveGroup(editedGroup);
			groupState = BeanState.LIST;
			return MAIN_PAGE;
		}
		catch (Exception e) {
			log.error("error on group saving", e);
			uiAlert.sendError();
			return null;
		}
	}

	public String saveStudent() {
		try {
			school.saveStudent(editedStudent);
			studentState = BeanState.LIST;
			updateStudentsList();
			return MAIN_PAGE;
		}
		catch (Exception e) {
			log.error("error on student save");
			uiAlert.sendError();
			return null;
		}
	}

	private void updateStudentsList() {
		if (viewedGroup != null) {
			viewedGroup = school.findGroupById(viewedGroup.getId());
		}
	}

	public String cancel() {
		studentState = BeanState.LIST;
		groupState = BeanState.LIST;
		return MAIN_PAGE;
	}

	public void deleteGroup() {
		ActionPerformer<Group,Long>groupRemover = new ActionPerformer<Group,Long>("Groups deleted", "Can't delete groups") {
			@Override
			protected void sendMessage(MessageSeverity severity, String title, String detail) {
				uiAlert.send(severity, title, detail);
			}

			@Override
			protected ErrorsSet<Group> checkAction(Long id) {
				return school.checkGroupDelete(id);
			}

			@Override
			protected void mainAction(Long id) {
				school.deleteGroup(id);
			}

			@Override
			protected String getName(Group entity) {
				return entity.getName();
			}
			
		};
		try {
			for (Entry<Long,Boolean> entry : checkedGroups.entrySet()) {
				if (entry.getValue()) {
					groupRemover.doAction(entry.getKey());
				}
			}
		}
		catch (Exception e) {
			log.error("error on group deleting", e);
			uiAlert.sendError();
		}
		finally {
			groupRemover.showResults();
			checkedGroups.clear();
		}
	}

	public void deleteStudent() {
		try{
			List<Long> idsToDelete = new ArrayList<Long>();
			for (Map.Entry<Long,Boolean> group : checkedStudents.entrySet()) {
				if (group.getValue()) {
					idsToDelete.add(group.getKey());
				}
			}
			int deletingCount = idsToDelete.size();
			if (deletingCount > 0) {
				school.deleteStudents(idsToDelete.toArray(new Long[deletingCount]));
				updateStudentsList();
			}
		}
		catch (Exception e) {
			log.error("error on student deleting", e);
			uiAlert.sendError();
		}
	}
	
	public Level[] getLevels() {
		return Level.values();
	}

	public List<Teacher> complateTeacher(String query) {
		List<Teacher> complate = new ArrayList<Teacher>();
		try{
			query = query.trim();
			for (Teacher teacher : staffService.getAllTeachers()) {
				String name = teacher.getFullName(); 
				if (name.contains(query)) {
					complate.add(teacher);
				}
			}
		}
		catch (Exception e) {
			log.error("error on teacher complating", e);
			uiAlert.sendError();
		}
		return complate;
	}

	public List<Group> complateGroup(String query) {
		List<Group> complate = new ArrayList<Group>();
		try{
			query = query.trim();
			for (Group group : school.getAllGroups()) {
				if (group.getName().contains(query)) {
					complate.add(group);
				}
			}
		}
		catch (Exception e){
			log.error("error on group complating",e);
			uiAlert.sendError();
		}
		return complate;
	}

	public Map<Long,Boolean> getCheckedGroups() {
		return checkedGroups;
	}
	public void setCheckedGroups(Map<Long,Boolean> checkedGroup) {
		this.checkedGroups = checkedGroup;
	}
	public Map<Long,Boolean> getCheckedStudents() {
		return checkedStudents;
	}
	public void setCheckedStudents(Map<Long,Boolean> checkedStudents) {
		this.checkedStudents = checkedStudents;
	}
	public Group getEditedGroup() {
		return editedGroup;
	}
	public void setEditedGroup(Group edited) {
		this.editedGroup = edited;
	}
	public Group getViewed() {
		return viewedGroup;
	}
	public List<Student> getStudents() {
		return viewedGroup.getStudents();
	}
	public Student getEditedStudent() {
		return editedStudent;
	}
	public void setEditedStudent(Student editedStudent) {
		this.editedStudent = editedStudent;
	}

	public Boolean getIsNewStudent() {
		return studentState.equals(BeanState.ADD);
	}
}
