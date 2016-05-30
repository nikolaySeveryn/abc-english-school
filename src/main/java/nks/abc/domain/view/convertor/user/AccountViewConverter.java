package nks.abc.domain.view.convertor.user;


import org.springframework.stereotype.Component;

import nks.abc.domain.entity.user.AccountInfo;
import nks.abc.domain.view.converter.Converter;
import nks.abc.domain.view.user.StaffView;

@Component
public class AccountViewConverter extends Converter<AccountInfo, StaffView>{
	
	public AccountInfo toDomain(StaffView dto) {
		AccountInfo entity = new AccountInfo();
		UserViewConvertor.toEntity(dto, entity);
		entity.isAdministrator(dto.getIsAdministrator());
		entity.isTeacher(dto.getIsTeacher());
		return entity;
	}
	
	public StaffView toView(AccountInfo entity) {
		StaffView dto = new StaffView();
		UserViewConvertor.toDTO(entity, dto);
		dto.setIsAdministrator(entity.isAdministrator());
		dto.setIsTeacher(entity.isTeacher());
		return dto;
	}
}
