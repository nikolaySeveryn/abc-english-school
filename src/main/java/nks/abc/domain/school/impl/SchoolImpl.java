package nks.abc.domain.school.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import nks.abc.dao.base.RepositoryException;
import nks.abc.dao.repository.user.GroupRepository;
import nks.abc.dao.repository.user.StudentRepository;
import nks.abc.dao.specification.factory.user.GroupSpecificationFactory;
import nks.abc.dao.specification.factory.user.StudentSpecificationFactory;
import nks.abc.domain.errors.DisregardOfValidationException;
import nks.abc.domain.errors.ErrorsSet;
import nks.abc.domain.exception.CrudException;
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

	@Override
	public List<Group> getAllGroups() {
		try {
			 return groupDAO.retrieveAll();
		}
		catch (RepositoryException e) {
			log.error("error on retrieve all groups", e);
			throw new CrudException("error on retrieve all groups", e);
		}
	}

	@Override
	@Transactional(readOnly = false)
	public void saveGroup(Group group) {
		try {
			if (group.getId() != null && group.getId() > 0L) {
				groupDAO.update(group);
				log.info("group has been updated: " + group);
			}
			else {
				groupDAO.insert(group);
				log.info("group has been added: " + group);
			}
		}
		catch (RepositoryException e) {
			log.error("error on saving group:" + group, e);
			throw new CrudException("error on saving group", e);
		}
	}

	@Override
	public Group findGroupById(Long id) {
		try {
			Group group = groupDAO.uniqueQuery(GroupSpecificationFactory.buildFactory().byId(id));
			return group;
		}
		catch (RepositoryException e) {
			log.error("error on getting group by id:" + id, e);
			throw new CrudException("error on getting group by id", e);
		}
	}
	
	

	@Override
	public ErrorsSet<Group> checkGroupDelete(Long id) {
		Group group = findGroupById(id);
		if(group == null){
			throw new IllegalArgumentException("Group you're trying to delete doesn't exist");
		}
		ErrorsSet<Group>errors = new ErrorsSet<Group>(group);
		if(group.hasMembers()){
			errors.addError("You can't delete group with have a mamber");
		}
		return errors;
	}

	@Override
	@Transactional(readOnly = false)
	public void deleteGroup(Long id) {
		try {
			ErrorsSet<Group> errors = checkGroupDelete(id);
			if(errors.hasErrors()){
				log.error("Got user errors:" + errors.toString());
				throw new DisregardOfValidationException(errors);
			}
			Group group = findGroupById(id);
			groupDAO.delete(group);
		}
		catch (RepositoryException e) {
			log.error("Error on deleting group id:" + id, e);
			throw new CrudException("Error on group deleting", e);
		}
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
				mailer.sendEmail(mailFactory.newStudent(account.getEmail(), password));
			}
			else {
				studentRepository.update(student);
				log.info("student has been updated: " + student);
			}
		}
		catch(RepositoryException e){
			log.error("error on saving student:" + student, e);
			throw new CrudException("error on saving student", e);
		}
	}
	
	@Override
	public Student findStudentById(Long id) {
		try{
			return studentRepository.uniqueQuery(StudentSpecificationFactory.buildFactory().byId(id));
		}
		catch(RepositoryException e) {
			log.error("error on getting student by id:" + id, e);
			throw new CrudException("error on getting student by id", e);
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
		catch(RepositoryException e) {
			log.error("error on deleting surdnets count:" + ids.length  ,e);
			throw new CrudException("error on students deleting",e);
		}
	}
}
