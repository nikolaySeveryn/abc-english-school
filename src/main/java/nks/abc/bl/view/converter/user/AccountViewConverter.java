package nks.abc.bl.view.converter.user;


import org.springframework.stereotype.Component;

import nks.abc.bl.domain.user.Account;
import nks.abc.bl.view.converter.Converter;
import nks.abc.bl.view.object.objects.user.StaffView;

@Component
public class AccountViewConverter extends Converter<Account, StaffView>{
	
	public Account toDomain(StaffView dto) {
		Account entity = new Account();
		UserViewConvertor.toEntity(dto, entity);
		entity.isAdministrator(dto.getIsAdministrator());
		entity.isTeacher(dto.getIsTeacher());
		return entity;
	}
	
	public StaffView toView(Account entity) {
		StaffView dto = new StaffView();
		UserViewConvertor.toDTO(entity, dto);
		dto.setIsAdministrator(entity.isAdministrator());
		dto.setIsTeacher(entity.isTeacher());
		return dto;
	}
}
