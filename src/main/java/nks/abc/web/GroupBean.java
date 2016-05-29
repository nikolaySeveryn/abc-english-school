package nks.abc.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;

import nks.abc.domain.dto.user.GroupDTO;
import nks.abc.domain.dto.user.StaffDTO;
import nks.abc.domain.dto.user.StudentDTO;
import nks.abc.domain.dto.user.UserDTO;
import nks.abc.domain.entity.user.Level;
import nks.abc.service.GroupService;
import nks.abc.service.StaffService;
import nks.abc.service.StudentService;
import nks.abc.web.common.enumeration.EditingMode;
import nks.abc.web.common.util.ErrorHandler;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
@ManagedBean
@SessionScoped
public class GroupBean implements Serializable {

	private static final String GROUP_EDIT_PAGE = "groupEdit.xhtml";
	private static final String MAIN_PAGE = "main.xhtml?faces-redirect=true";
	private static final String STUDENT_EDIT_PAGE = "studentEdit.xhtml";

	private static final Logger log = Logger.getLogger(GroupBean.class);

	@Autowired
	private GroupService groupService;
	@Autowired
	private StaffService staffService;
	@Autowired
	private StudentService studentService;

	private ErrorHandler errorHandler;

	private Map<Long,Boolean> checkedGroups = new HashMap<Long,Boolean>();
	private Map<Long,Boolean> checkedStudents = new HashMap<Long,Boolean>();
	private GroupDTO editedGroup;
	private GroupDTO viewedGroup;
	private StudentDTO editedStudent;
	private EditingMode groupMode = EditingMode.NONE;
	private EditingMode studentMode = EditingMode.NONE;

	@Autowired
	public void setErrorHandler(ErrorHandler errorHandler) {
		this.errorHandler = errorHandler;
		this.errorHandler.loggerFor(this.getClass());
	}

	public List<GroupDTO> getGroupList() {
		try {
			return groupService.getGroups();
		}
		catch (Exception e) {
			errorHandler.handle(e);
			return new ArrayList<GroupDTO>();
		}
	}

	public String addGroup() {
		editedGroup = new GroupDTO();
		groupMode = EditingMode.ADD;
		return GROUP_EDIT_PAGE;
	}

	public String addStudent() {
		editedStudent = UserDTO.newStudent();
		if (viewedGroup != null) {
			List<GroupDTO> groups = editedStudent.getGroups();
			groups.add(viewedGroup);
			editedStudent.setGroups(groups);
		}
		studentMode = EditingMode.ADD;
		return STUDENT_EDIT_PAGE;
	}

	public void viewGroup(GroupDTO group) {
		this.viewedGroup = group;
		checkedStudents.clear();
	}

	public String editGroup(GroupDTO group) {
		System.out.println("edit group");
		editedGroup = group;
		groupMode = EditingMode.EDIT;
		return GROUP_EDIT_PAGE;
	}

	public String editStudent(StudentDTO student) {
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

	public List<StaffDTO> complateTeacher(String query) {
		query = query.trim();
		List<StaffDTO> complate = new ArrayList<StaffDTO>();
		for (StaffDTO teacher : staffService.getAllTeachers()) {
			String name = teacher.getFirstName() + " " + teacher.getSirName() + " " + teacher.getPatronomic();
			if (name.contains(query)) {
				complate.add(teacher);
			}
		}
		return complate;
	}

	public List<GroupDTO> complateGroup(String query) {
		query = query.trim();
		List<GroupDTO> complate = new ArrayList<GroupDTO>();
		for (GroupDTO group : groupService.getGroups()) {
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
	public GroupDTO getEditedGroup() {
		return editedGroup;
	}
	public void setEditedGroup(GroupDTO edited) {
		this.editedGroup = edited;
	}
	public GroupDTO getViewed() {
		return viewedGroup;
	}
	public StudentDTO getEditedStudent() {
		return editedStudent;
	}
	public void setEditedStudent(StudentDTO editedStudent) {
		this.editedStudent = editedStudent;
	}

	public Boolean getIsNewStudent() {
		return studentMode.equals(EditingMode.ADD);
	}
}
