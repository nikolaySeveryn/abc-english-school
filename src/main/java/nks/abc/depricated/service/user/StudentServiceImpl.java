package nks.abc.depricated.service.user;

import java.util.Arrays;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import nks.abc.core.exception.handler.ErrorHandler;
import nks.abc.core.exception.service.SendMailException;
import nks.abc.dao.repository.user.StudentRepository;
import nks.abc.dao.specification.factory.user.StudentSpecificationFactory;
import nks.abc.depricated.service.message.MailFactory;
import nks.abc.depricated.service.message.MailService;
import nks.abc.domain.user.Account;
import nks.abc.domain.user.Student;

@Service
@Transactional(readOnly=true)
public class StudentServiceImpl implements StudentService {
	
	private static final Logger log = Logger.getLogger(StudentServiceImpl.class);
	
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
	@Transactional(readOnly=false)
	public void save(Student student) {
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
	public Student getById(Long id) {
		try{
			return getStudentById(id);
		}
		catch(Exception e) {
			errorHandler.handle(e, "get student by id=" + id);
			return null;
		}
	}

	@Override
	@Transactional(readOnly=false)
	public void delete(Long ...  ids) {
		try{
			for(Long id: ids) {
				studentRepository.delete(getStudentById(id));
			}
		}
		catch(Exception e) {
			errorHandler.handle(e, "delete students: " + Arrays.toString(ids));
		}
	}

	private Student getStudentById(Long id) {
		return studentRepository.uniqueQuery(StudentSpecificationFactory.buildFactory().byId(id));
	}
}
