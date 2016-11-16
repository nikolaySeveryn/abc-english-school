package nks.abc.web.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;

import nks.abc.core.exception.handler.ErrorHandler;
import nks.abc.domain.school.Group;
import nks.abc.domain.school.Level;
import nks.abc.domain.school.School;
import nks.abc.domain.school.impl.GroupImpl;
import nks.abc.domain.user.HumanResources;
import nks.abc.domain.user.Student;
import nks.abc.domain.user.Teacher;
import nks.abc.domain.user.factory.UserFactory;
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
	private School school;
	@Autowired
	private HumanResources staffService;

	private Map<Long,Boolean> checkedGroups = new HashMap<Long,Boolean>();
	private Map<Long,Boolean> checkedStudents = new HashMap<Long,Boolean>();
	private Group editedGroup;
	private Group viewedGroup;
	private Student editedStudent;
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
			return school.getGroups();
		}
		catch (Exception e) {
			errorHandler.handle(e);
			return new ArrayList<Group>();
		}
	}

	public String addGroup() {
		editedGroup = new GroupImpl();
		groupMode = EditingMode.ADD;
		return GROUP_EDIT_PAGE;
	}

	public String addStudent() {
		editedStudent = UserFactory.createStudent();
		if (viewedGroup != null) {
			Set<Group> groups = editedStudent.getGroups();
			groups.add(viewedGroup);
			editedStudent.setGroups(groups);
		}
		studentMode = EditingMode.ADD;
		return STUDENT_EDIT_PAGE;
	}

	public void viewGroup(Group group) {
		this.viewedGroup = group;
		checkedStudents.clear();
	}

	public String editGroup(Group group) {
		editedGroup = group;
		groupMode = EditingMode.EDIT;
		return GROUP_EDIT_PAGE;
	}

	public String editStudent(Long studentId) {
		editedStudent = school.findStudentById(studentId);
		studentMode = EditingMode.EDIT;
		return STUDENT_EDIT_PAGE;
	}

	public String saveGroup() {
		try {
			school.saveGroup(editedGroup);
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
			school.saveStudent(editedStudent);
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
			viewedGroup = school.findGroupById(viewedGroup.getId());
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
			school.deleteGroups(idsToDelete.toArray(new Long[deletingCount]));
		}
		checkedGroups.clear();
	}

	public void deleteStudent() {
		List<Long> idsToDelete = seekSelectedItems(checkedStudents);
		int deletingCount = idsToDelete.size();
		if (deletingCount > 0) {
			school.deleteStudents(idsToDelete.toArray(new Long[deletingCount]));
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

	public List<Teacher> complateTeacher(String query) {
		query = query.trim();
		List<Teacher> complate = new ArrayList<Teacher>();
		for (Teacher teacher : staffService.getAllTeachers()) {
			String name = teacher.getFullName(); 
			if (name.contains(query)) {
				complate.add(teacher);
			}
		}
		return complate;
	}

	public List<Group> complateGroup(String query) {
		query = query.trim();
		List<Group> complate = new ArrayList<Group>();
		for (Group group : school.getGroups()) {
			if (group.getName().contains(query)) {
				complate.add(group);
			}
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
	public Student getEditedStudent() {
		return editedStudent;
	}
	public void setEditedStudent(Student editedStudent) {
		this.editedStudent = editedStudent;
	}

	public Boolean getIsNewStudent() {
		return studentMode.equals(EditingMode.ADD);
	}
}
