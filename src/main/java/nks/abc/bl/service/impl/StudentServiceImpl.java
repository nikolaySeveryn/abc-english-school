package nks.abc.bl.service.impl;

import java.util.Arrays;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import nks.abc.bl.domain.user.Student;
import nks.abc.bl.service.StudentService;
import nks.abc.bl.view.converter.user.GroupViewConverter;
import nks.abc.bl.view.converter.user.StudentViewConverter;
import nks.abc.bl.view.object.objects.user.StudentView;
import nks.abc.core.exception.handler.ErrorHandler;
import nks.abc.dao.repository.user.StudentRepository;

@Service
@Transactional(readOnly=true)
public class StudentServiceImpl implements StudentService {
	
	private static final Logger log = Logger.getLogger(StudentServiceImpl.class);
	
	@Autowired
	private StudentRepository studentRepository;
	
	@Autowired
	private StudentViewConverter studentConverter;
	
	private ErrorHandler errorHandler;
	
	@Autowired
	@Qualifier("serviceErrorHandler")
	public void setErrorHandler(ErrorHandler errorHandler){
		this.errorHandler = errorHandler;
		this.errorHandler.loggerFor(this.getClass());
	}

	@Override
	@Transactional(readOnly=false)
	public void save(StudentView studentDTO) {
		studentConverter.setRelativeConvertersPattern(new GroupViewConverter());
		Student student = studentConverter.toDomain(studentDTO);
		try{
			if(student.isNew()){
				log.info("Insert new student: " + student);
				student.updatePassword(studentDTO.getPassword());
				studentRepository.insert(student);
			}
			else{
				log.info("Update student: " + student);
				studentRepository.update(student);
			}
		}
		catch(Exception e){
			errorHandler.handle(e, "save student: " + studentDTO);
		}
	}

	@Override
	public StudentView getById(Long id) {
		try{
			System.out.println("error handler: " + this.errorHandler);
			Student student = getStudentDomainById(id);
			studentConverter.setRelativeConvertersPattern(new GroupViewConverter());
			return studentConverter.toView(student);
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
			for(Long id: ids){
				studentRepository.delete(getStudentDomainById(id));
			}
		}
		catch(Exception e) {
			errorHandler.handle(e, "delete students: " + Arrays.toString(ids));
		}
	}

	private Student getStudentDomainById(Long id) {
		return studentRepository.uniqueQuery(studentRepository.specifications().byId(id));
	}
}
