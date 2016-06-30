package nks.abc.domain.view.converter.user;

import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import nks.abc.dao.repository.user.TeacherRepository;
import nks.abc.domain.entity.user.Account;
import nks.abc.domain.entity.user.Group;
import nks.abc.domain.entity.user.Student;
import nks.abc.domain.entity.user.Teacher;
import nks.abc.domain.view.converter.Converter;
import nks.abc.domain.view.converter.ConvertersHolder;
import nks.abc.domain.view.object.objects.user.GroupView;
import nks.abc.domain.view.object.objects.user.StaffView;

@Component
public class GroupViewConverter extends Converter<Group,GroupView> {

	@Autowired
	private TeacherRepository teacherDAO;

	@Override
	public GroupView toView(Group bo) {
		GroupView dto = new GroupView();
		dto.setId(bo.getId());
		dto.setLevel(bo.getLevel());
		dto.setName(bo.getName());
		dto.setTarif(bo.getFloatTarif());

		ConvertersHolder relativeConverters = getRelativeConverters();
		dto.setTeacher((StaffView) relativeConverters.pullConverter(AccountViewConverter.class).toView(bo.getTeacher().getAccountInfo()));
		dto.setStudents(relativeConverters.pullConverter(StudentViewConverter.class).toView(bo.getStudents()));
		return dto;
	}

	@Override
	public Group toDomain(GroupView dto) {
		Group bo = new Group();
		bo.setId(dto.getId());
		bo.setLevel(dto.getLevel());
		bo.setName(dto.getName());
		bo.setFloatTarif(dto.getTarif());

		ConvertersHolder relativeConverters = getRelativeConverters();
		Account account = (Account) relativeConverters.pullConverter(AccountViewConverter.class).toDomain(dto.getTeacher());
		if(account != null){
			Teacher teacher = teacherDAO.uniqueQuery(teacherDAO.specifications().byAccount(account));
			bo.setTeacher(teacher);
		}
		//TODO: why we need convert student?
		List<Student> students = relativeConverters.pullConverter(StudentViewConverter.class).toDomain(dto.getStudents());
		bo.setStudents(new HashSet<Student>(students));
//		bo.setStudents(new HashSet<Student>((Collection<Student>) studentConverter.toDomain(dto.getStudents())));
		return bo;
	}
}
