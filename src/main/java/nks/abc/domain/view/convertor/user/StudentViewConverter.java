package nks.abc.domain.view.convertor.user;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Component;

import nks.abc.domain.entity.user.Group;
import nks.abc.domain.entity.user.PersonalInfo;
import nks.abc.domain.entity.user.Student;
import nks.abc.domain.entity.user.User;
import nks.abc.domain.view.converter.Converter;
import nks.abc.domain.view.user.ParentInfoView;
import nks.abc.domain.view.user.StudentView;

@Component
public class StudentViewConverter extends Converter<Student, StudentView> {

	
	@Override
	public StudentView toView(Student bo) {
		StudentView dto = new StudentView();
		UserViewConvertor.toDTO(bo.getAccountInfo(), dto);
		dto.setId(bo.getId());
		dto.setParent(parentToDTO(bo.getParent()));
		
		dto.setGroups(getRelativeConverters().pullConverter(GroupViewConverter.class).toView(bo.getGroups()));
		dto.setDiscount(bo.getDiscount());
		dto.setMoneyBalance(bo.getMoneyBalance());
		return dto;
	}
	
	@Override
	public Student toDomain(StudentView dto) {
		Student bo = User.newStudent();
		UserViewConvertor.toEntity(dto, bo.getAccountInfo());
		bo.setId(dto.getId());
		bo.setParent(parentToDomain(dto.getParent()));
		Set<Group>groups = new HashSet<Group>(getRelativeConverters().pullConverter(GroupViewConverter.class).toDomain(dto.getGroups()));
		bo.setGroups(groups);
		bo.setDiscount(dto.getDiscount());
		bo.setMoneyBalance(dto.getMoneyBalance());
		return bo;
	}

	private ParentInfoView parentToDTO(PersonalInfo bo) {
		ParentInfoView dto = new ParentInfoView();
		dto.setBirthday(bo.getBirthday());
		dto.setEmail(bo.getEmail());
		dto.setFirstName(bo.getFirstName());
		dto.setId(bo.getId());
		dto.setPatronomic(bo.getPatronomic());
		dto.setPhoneNum(bo.getPhoneNum());
		dto.setSirName(bo.getSirName());
		return dto;
	}
	
	private PersonalInfo parentToDomain(ParentInfoView dto){
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
