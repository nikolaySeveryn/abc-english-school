package nks.abc.domain.dto.convertor.user;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Component;

import nks.abc.domain.dto.converter.Converter;
import nks.abc.domain.dto.user.ParentInfoDTO;
import nks.abc.domain.dto.user.StudentDTO;
import nks.abc.domain.entity.user.Group;
import nks.abc.domain.entity.user.PersonalInfo;
import nks.abc.domain.entity.user.Student;
import nks.abc.domain.entity.user.User;

@Component
public class StudentDTOConverter extends Converter<Student, StudentDTO> {

	
	@Override
	public StudentDTO toDTO(Student bo) {
		StudentDTO dto = new StudentDTO();
		UserDTOConvertor.toDTO(bo.getAccountInfo(), dto);
		dto.setId(bo.getId());
		dto.setParent(parentToDTO(bo.getParent()));
		
		dto.setGroups(getRelativeConverters().pullConverter(GroupDTOConverter.class).toDTO(bo.getGroups()));
		dto.setDiscount(bo.getDiscount());
		dto.setMoneyBalance(bo.getMoneyBalance());
		return dto;
	}
	
	@Override
	public Student toDomain(StudentDTO dto) {
		Student bo = User.newStudent();
		UserDTOConvertor.toEntity(dto, bo.getAccountInfo());
		bo.setId(dto.getId());
		bo.setParent(parentToDomain(dto.getParent()));
		Set<Group>groups = new HashSet<Group>(getRelativeConverters().pullConverter(GroupDTOConverter.class).toDomain(dto.getGroups()));
		bo.setGroups(groups);
		bo.setDiscount(dto.getDiscount());
		bo.setMoneyBalance(dto.getMoneyBalance());
		return bo;
	}

	private ParentInfoDTO parentToDTO(PersonalInfo bo) {
		ParentInfoDTO dto = new ParentInfoDTO();
		dto.setBirthday(bo.getBirthday());
		dto.setEmail(bo.getEmail());
		dto.setFirstName(bo.getFirstName());
		dto.setId(bo.getId());
		dto.setPatronomic(bo.getPatronomic());
		dto.setPhoneNum(bo.getPhoneNum());
		dto.setSirName(bo.getSirName());
		return dto;
	}
	
	private PersonalInfo parentToDomain(ParentInfoDTO dto){
		PersonalInfo bo = new PersonalInfo();
		bo.setBirthday(dto.getBirthday());
		bo.setEmail(dto.getEmail());
		bo.setFirstName(dto.getFirstName());
		bo.setId(dto.getId());
		bo.setPatronomic(dto.getPatronomic());
		bo.setPhoneNum(dto.getPhoneNum());
		bo.setSirName(dto.getSirName());
		return bo;
	}

}
