package nks.abc.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sun.faces.facelets.Facelet;

import nks.abc.dao.exception.DAOException;
import nks.abc.dao.repository.user.StudentRepository;
import nks.abc.domain.dto.convertor.user.GroupDTOConverter;
import nks.abc.domain.dto.convertor.user.StudentDTOConverter;
import nks.abc.domain.dto.user.StudentDTO;
import nks.abc.domain.entity.user.Student;
import nks.abc.domain.exception.ConversionException;
import nks.abc.service.StudentService;
import nks.abc.service.exception.ServiceException;
import nks.abc.web.common.converter.GroupConverter;

@Service
@Transactional(readOnly=true)
public class StudentServiceImpl implements StudentService {
	
	private static final Logger log = Logger.getLogger(StudentServiceImpl.class);
	
	@Autowired
	private StudentRepository studentRepository;
	
	@Autowired
	private StudentDTOConverter studentConverter;

	@Override
	@Transactional(readOnly=false)
	public void save(StudentDTO studentDTO) {
		//TODO: unique login
		studentConverter.setRelativeConvertersPattern(new GroupDTOConverter());
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
		catch(DAOException de){
			log.error("Error on saving student", de);
			throw new ServiceException("Error on saving student", de);
		}
	}

	@Override
	public StudentDTO getById(Long id) {
		try{
			Student student = getStudentDomainById(id);
			studentConverter.setRelativeConvertersPattern(new GroupDTOConverter());
			return studentConverter.toDTO(student);
		}
		//TODO: exception refactoring
		catch(DAOException de) {
			log.error("error during getting student by id", de);
			throw new ServiceException("error during getting student by id", de);
		}
		catch (ConversionException ce) {
			log.error("error during converting student", ce);
			throw new ServiceException("error during converting student", ce);
		}
	}

	private Student getStudentDomainById(Long id) {
		return studentRepository.uniqueQuery(studentRepository.getSpecificationFactory().byId(id));
	}

	@Override
	@Transactional(readOnly=false)
	public void delete(Long ...  ids) {
		try{
			for(Long id: ids){
				studentRepository.delete(getStudentDomainById(id));
			}
		}
		catch(DAOException de) {
			log.error("error during deleting student", de);
			throw new ServiceException("error during deleting student", de);
		}
		catch (ConversionException ce) {
			log.error("error during converting student", ce);
			throw new ServiceException("error during converting student", ce);
		}
	}

}
