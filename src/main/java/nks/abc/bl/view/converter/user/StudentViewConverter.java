package nks.abc.bl.view.converter.user;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Component;

import nks.abc.bl.domain.user.Group;
import nks.abc.bl.domain.user.Parent;
import nks.abc.bl.domain.user.PersonalInfo;
import nks.abc.bl.domain.user.Student;
import nks.abc.bl.domain.user.User;
import nks.abc.bl.view.converter.Converter;
import nks.abc.bl.view.object.objects.user.ParentInfoView;
import nks.abc.bl.view.object.objects.user.StudentView;

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

	private ParentInfoView parentToDTO(Parent bo) {
		ParentInfoView dto = new ParentInfoView();
		dto.setBirthday(bo.getPersonalInfo().getBirthday());
		dto.setEmail(bo.getEmail());
		dto.setFirstName(bo.getPersonalInfo().getFirstName());
		dto.setId(bo.getId());
		dto.setPatronomic(bo.getPersonalInfo().getPatronomic());
		dto.setPhoneNum(bo.getPersonalInfo().getPhoneNum());
		dto.setSirName(bo.getPersonalInfo().getSirName());
		dto.setPersonalInfoId(bo.getPersonalInfo().getId());
		return dto;
	}
	
	private Parent parentToDomain(ParentInfoView dto) {
		Parent bo = Parent.newParent();
		java.sql.Date sqlDate = null;
		if(dto.getBirthday() != null){
			sqlDate = new java.sql.Date(dto.getBirthday().getTime());
		}
		bo.getPersonalInfo().setId(dto.getPersonalInfoId());
		bo.getPersonalInfo().setBirthday(sqlDate);
		bo.setEmail(dto.getEmail());
		bo.getPersonalInfo().setFirstName(dto.getFirstName());
		bo.setId(dto.getId());
		bo.getPersonalInfo().setPatronomic(dto.getPatronomic());
		bo.getPersonalInfo().setPhoneNum(dto.getPhoneNum());
		bo.getPersonalInfo().setSirName(dto.getSirName());
		return bo;
	}
}
