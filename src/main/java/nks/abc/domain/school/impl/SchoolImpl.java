package nks.abc.domain.school.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import nks.abc.core.exception.handler.ErrorHandler;
import nks.abc.core.exception.service.RemoveDenidedException;
import nks.abc.core.exception.service.SendMailException;
import nks.abc.dao.repository.user.GroupRepository;
import nks.abc.dao.repository.user.StudentRepository;
import nks.abc.dao.specification.factory.user.GroupSpecificationFactory;
import nks.abc.dao.specification.factory.user.StudentSpecificationFactory;
import nks.abc.domain.message.MailFactory;
import nks.abc.domain.message.MailService;
import nks.abc.domain.school.Group;
import nks.abc.domain.school.School;
import nks.abc.domain.user.Account;
import nks.abc.domain.user.Student;

@Service
@Transactional(readOnly = true)
public class SchoolImpl implements School {

	private static final Logger log = Logger.getLogger(SchoolImpl.class);

	@Autowired
	private GroupRepository groupDAO;
	@Autowired
	private StudentRepository studentRepository;
	@Autowired
	private MailService mailer;
	@Autowired
	private MailFactory mailFactory;

	private ErrorHandler errorHandler;
	
	@Autowired
	@Qualifier("serviceErrorHandler")
	public void setErrorHandler(ErrorHandler errorHandler){
		this.errorHandler = errorHandler;
		this.errorHandler.loggerFor(this.getClass());
	}

	@Override
	public List<Group> getGroups() {
		List<Group> groups = new ArrayList<Group>();
		try {
			 groups = groupDAO.retrieveAll();
		}
		catch (Exception e) {
			errorHandler.handle(e, "get all groups");
		}
		return groups;
	}

	@Override
	@Transactional(readOnly = false)
	public void saveGroup(Group group) {
		try {
			if (group.getId() != null && group.getId() > 0L) {
				log.info("updating group: " + group);
				groupDAO.update(group);
			}
			else {
				log.info("inserting group: " + group);
				groupDAO.insert(group);
			}
		}
		catch (Exception e) {
			errorHandler.handle(e, "save group: " + group);
		}
	}

	@Override
	public Group findGroupById(Long id) {
		try {
			Group group = groupDAO.uniqueQuery(GroupSpecificationFactory.buildFactory().byId(id));
			return group;
		}
		catch (Exception e) {
			errorHandler.handle(e, "get group by id = " + id);
			return null;
		}
	}

	@Override
	@Transactional(readOnly = false)
	public void deleteGroups(Long... ids) {
		try {
			for (Long id : ids) {
				deleteGroup(id);
			}
		}
		catch (Exception e) {
			errorHandler.handle(e, "delete groups: " + Arrays.toString(ids));
		}
	}
	
	private void deleteGroup(Long id){
		Group group = findGroupById(id);
		if(group.hasMembers()){
			//TODO: stop to use exception as a normal behavior
			throw new RemoveDenidedException("You can't delete not empty group");
		}
		groupDAO.delete(group);
	}
	
	@Override
	@Transactional(readOnly=false)
	public void saveStudent(Student student) {
		try {
			if(student.isNew()){
				log.info("Insert new student: " + student);
				Account account = student.getAccount();
				String password = account.updatePasswordToRandom();
				studentRepository.insert(student);
				try {
					mailer.sendEmail(mailFactory.newStudent(account.getEmail(), password));
				}
				catch (SendMailException e) {
					log.warn("Email with password wasn't sent to student + " + account.getFullName() + " - " + account.getEmail());
				}
			}
			else {
				log.info("Update student: " + student);
				studentRepository.update(student);
			}
		}
		catch(Exception e){
			errorHandler.handle(e, "save student: " + student);
		}
	}
	
	@Override
	public Student findStudentById(Long id) {
		try{
			return studentRepository.uniqueQuery(StudentSpecificationFactory.buildFactory().byId(id));
		}
		catch(Exception e) {
			errorHandler.handle(e, "get student by id=" + id);
			return null;
		}
	}
	
	@Override
	@Transactional(readOnly=false)
	public void deleteStudents(Long ...  ids) {
		try{
			for(Long id: ids) {
				studentRepository.delete(findStudentById(id));
			}
		}
		catch(Exception e) {
			errorHandler.handle(e, "delete students: " + Arrays.toString(ids));
		}
	}
}
