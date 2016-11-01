package nks.abc.depricated.view.converter.user;


import org.springframework.stereotype.Component;

import nks.abc.depricated.view.converter.Converter;
import nks.abc.depricated.view.object.objects.user.StaffView;
import nks.abc.domain.user.Account;
import nks.abc.domain.user.impl.AccountImpl;

@Component
public class AccountViewConverter extends Converter<Account, StaffView>{
	
	public Account toDomain(StaffView dto) {
		Account entity = new AccountImpl();
//		UserViewConvertor.toEntity(dto, entity);
//		entity.setIsAdministrator(dto.getIsAdministrator());
//		entity.setIsTeacher(dto.getIsTeacher());
		return entity;
	}
	
	public StaffView toView(Account entity) {
		StaffView dto = new StaffView();
//		UserViewConvertor.toDTO(entity, dto);
//		dto.setIsAdministrator(entity.getIsAdministrator());
//		dto.setIsTeacher(entity.getIsTeacher());
		return dto;
	}
}
