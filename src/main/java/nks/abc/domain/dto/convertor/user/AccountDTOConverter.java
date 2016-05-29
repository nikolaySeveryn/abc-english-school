package nks.abc.domain.dto.convertor.user;


import org.springframework.stereotype.Component;

import nks.abc.domain.dto.converter.Converter;
import nks.abc.domain.dto.user.StaffDTO;
import nks.abc.domain.entity.user.AccountInfo;

@Component
public class AccountDTOConverter extends Converter<AccountInfo, StaffDTO>{
	
	public AccountInfo toDomain(StaffDTO dto) {
		AccountInfo entity = new AccountInfo();
		UserDTOConvertor.toEntity(dto, entity);
		entity.isAdministrator(dto.getIsAdministrator());
		entity.isTeacher(dto.getIsTeacher());
		return entity;
	}
	
	public StaffDTO toDTO(AccountInfo entity) {
		StaffDTO dto = new StaffDTO();
		UserDTOConvertor.toDTO(entity, dto);
		dto.setIsAdministrator(entity.isAdministrator());
		dto.setIsTeacher(entity.isTeacher());
		return dto;
	}
}
