package nks.abc.web.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;

import nks.abc.core.exception.handler.ErrorHandler;
import nks.abc.depricated.service.user.GroupService;
import nks.abc.depricated.service.user.StaffService;
import nks.abc.depricated.service.user.StudentService;
import nks.abc.depricated.view.factory.UserViewFactory;
import nks.abc.depricated.view.object.objects.user.GroupView;
import nks.abc.depricated.view.object.objects.user.StaffView;
import nks.abc.depricated.view.object.objects.user.StudentView;
import nks.abc.domain.user.Level;
import nks.abc.domain.user.impl.Group;
import nks.abc.web.common.enumeration.EditingMode;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
@ManagedBean
@SessionScoped
public class StudentBean implements Serializable {

	private static final String GROUP_EDIT_PAGE = "groupEdit.xhtml";
	private static final String MAIN_PAGE = "main.xhtml?faces-redirect=true";
	private static final String STUDENT_EDIT_PAGE = "studentEdit.xhtml";

	private static final Logger log = Logger.getLogger(StudentBean.class);
	private ErrorHandler errorHandler;

	@Autowired
	private GroupService groupService;
	@Autowired
	private StaffService staffService;
	@Autowired
	private StudentService studentService;

	private Map<Long,Boolean> checkedGroups = new HashMap<Long,Boolean>();
	private Map<Long,Boolean> checkedStudents = new HashMap<Long,Boolean>();
	private Group editedGroup;
	private Group viewedGroup;
	private StudentView editedStudent;
	private EditingMode groupMode = EditingMode.NONE;
	private EditingMode studentMode = EditingMode.NONE;

	@Autowired
	@Qualifier("webErrorHandler")
	public void setErrorHandler(ErrorHandler errorHandler) {
		this.errorHandler = errorHandler;
		this.errorHandler.loggerFor(this.getClass());
	}

	public List<Group> getGroupList() {
		try {
			return groupService.getGroups();
		}
		catch (Exception e) {
			errorHandler.handle(e);
			return new ArrayList<Group>();
		}
	}

	public String addGroup() {
		editedGroup = new Group();
		groupMode = EditingMode.ADD;
		return GROUP_EDIT_PAGE;
	}

	public String addStudent() {
		editedStudent = UserViewFactory.newStudent();
		if (viewedGroup != null) {
//			List<Group> groups = editedStudent.getGroups();
//			groups.add(viewedGroup);
//			editedStudent.setGroups(groups);
		}
		studentMode = EditingMode.ADD;
		return STUDENT_EDIT_PAGE;
	}

	public void viewGroup(Group group) {
		this.viewedGroup = group;
		checkedStudents.clear();
	}

	public String editGroup(Group group) {
		System.out.println("edit group");
		editedGroup = group;
		groupMode = EditingMode.EDIT;
		return GROUP_EDIT_PAGE;
	}

	public String editStudent(StudentView student) {
		System.out.println("edit student");
		editedStudent = studentService.getById(student.getId()); // reload student to get groups
		studentMode = EditingMode.EDIT;
		return STUDENT_EDIT_PAGE;
	}

	public String saveGroup() {
		try {
			groupService.saveGroup(editedGroup);
			groupMode = EditingMode.NONE;
			return MAIN_PAGE;
		}
		catch (Exception e) {
			errorHandler.handle(e);
			return null;
		}
	}

	public String saveStudent() {
		try {
			studentService.save(editedStudent);
			studentMode = EditingMode.NONE;
			updateStudentsList();
			return MAIN_PAGE;
		}
		catch (Exception e) {
			errorHandler.handle(e);
			return null;
		}
	}

	private void updateStudentsList() {
		if (viewedGroup != null) {
			viewedGroup = groupService.getById(viewedGroup.getId());
		}
	}

	public String cancel() {
		studentMode = EditingMode.NONE;
		groupMode = EditingMode.NONE;
		return MAIN_PAGE;
	}

	public void deleteGroup() {
		List<Long> idsToDelete = seekSelectedItems(checkedGroups);
		int deletingCount = idsToDelete.size();
		if (deletingCount > 0) {
			groupService.deleteGroups(idsToDelete.toArray(new Long[deletingCount]));
		}
	}

	public void deleteStudent() {
		List<Long> idsToDelete = seekSelectedItems(checkedStudents);
		int deletingCount = idsToDelete.size();
		if (deletingCount > 0) {
			studentService.delete(idsToDelete.toArray(new Long[deletingCount]));
			updateStudentsList();
		}
	}
	
	private List<Long> seekSelectedItems(Map<Long,Boolean> allElement) {
		List<Long> idsToDelete = new ArrayList<Long>();
		for (Map.Entry<Long,Boolean> group : allElement.entrySet()) {
			if (group.getValue()) {
				idsToDelete.add(group.getKey());
			}
		}
		return idsToDelete;
	}

	public Level[] getLevels() {
		return Level.values();
	}

	public List<StaffView> complateTeacher(String query) {
		query = query.trim();
		List<StaffView> complate = new ArrayList<StaffView>();
//		for (StaffView teacher : staffService.getAllTeachers()) {
//			String name = teacher.getFirstName() + " " + teacher.getSirName() + " " + teacher.getPatronomic();
//			if (name.contains(query)) {
//				complate.add(teacher);
//			}
//		}
		return complate;
	}

	public List<GroupView> complateGroup(String query) {
		query = query.trim();
		List<GroupView> complate = new ArrayList<GroupView>();
//		for (GroupView group : groupService.getGroups()) {
//			if (group.getName().contains(query)) {
//				complate.add(group);
//			}
//		}
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
	public StudentView getEditedStudent() {
		return editedStudent;
	}
	public void setEditedStudent(StudentView editedStudent) {
		this.editedStudent = editedStudent;
	}

	public Boolean getIsNewStudent() {
		return studentMode.equals(EditingMode.ADD);
	}
}
