package nks.abc.domain.dto.convertor.user;

import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import nks.abc.dao.repository.user.TeacherRepository;
import nks.abc.domain.dto.converter.Converter;
import nks.abc.domain.dto.converter.ConvertersHolder;
import nks.abc.domain.dto.user.GroupDTO;
import nks.abc.domain.dto.user.StaffDTO;
import nks.abc.domain.entity.user.AccountInfo;
import nks.abc.domain.entity.user.Group;
import nks.abc.domain.entity.user.Student;
import nks.abc.domain.entity.user.Teacher;

@Component
public class GroupDTOConverter extends Converter<Group,GroupDTO> {

	@Autowired
	private TeacherRepository teacherDAO;

	@Override
	public GroupDTO toDTO(Group bo) {
		GroupDTO dto = new GroupDTO();
		dto.setId(bo.getId());
		dto.setLevel(bo.getLevel());
		dto.setName(bo.getName());
		dto.setTarif(bo.getFloatTarif());

		ConvertersHolder relativeConverters = getRelativeConverters();
		dto.setTeacher((StaffDTO) relativeConverters.pullConverter(AccountDTOConverter.class).toDTO(bo.getTeacher().getAccountInfo()));
		dto.setStudents(relativeConverters.pullConverter(StudentDTOConverter.class).toDTO(bo.getStudents()));
		return dto;
	}

	@Override
	public Group toDomain(GroupDTO dto) {
		Group bo = new Group();
		bo.setId(dto.getId());
		bo.setLevel(dto.getLevel());
		bo.setName(dto.getName());
		bo.setFloatTarif(dto.getTarif());

		ConvertersHolder relativeConverters = getRelativeConverters();
		AccountInfo account = (AccountInfo) relativeConverters.pullConverter(AccountDTOConverter.class).toDomain(dto.getTeacher());
		if(account != null){
			Teacher teacher = teacherDAO.uniqueQuery(teacherDAO.getSpecificaitonFactory().byAccount(account));
			bo.setTeacher(teacher);
		}
		//TODO: why we need convert student?
		List<Student> students = relativeConverters.pullConverter(StudentDTOConverter.class).toDomain(dto.getStudents());
		bo.setStudents(new HashSet<Student>(students));
//		bo.setStudents(new HashSet<Student>((Collection<Student>) studentConverter.toDomain(dto.getStudents())));
		return bo;
	}
}
