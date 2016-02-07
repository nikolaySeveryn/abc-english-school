package nks.abc.domain.dto.convertor.user;

import java.util.List;
import nks.abc.domain.dto.user.StaffDTO;
import nks.abc.domain.entity.user.AccountInfo;

public class StaffDTOConvertor {
	
	public static void toEntity(StaffDTO dto, AccountInfo entity) {
		UserDTOConvertor.toEntity(dto, entity);
		entity.isAdministrator(dto.getIsAdministrator());
		entity.isTeacher(dto.getIsTeacher());
	}
	
	public static void toDTO(AccountInfo entity, StaffDTO dto) {
		UserDTOConvertor.toDTO(entity, dto);
		dto.setIsAdministrator(entity.isAdministrator());
		dto.setIsTeacher(entity.isTeacher());
	}
	
	public static void toEntity(List<StaffDTO> dtos, List<AccountInfo> entities) {
		for(StaffDTO dto : dtos){
			AccountInfo entity = new AccountInfo();
			toEntity(dto, entity);
			entities.add(entity);
		}
	}
	
	public static void toDTO(List<AccountInfo> entities, List<StaffDTO> dtos) {
		for(AccountInfo entity : entities){
			StaffDTO dto = new StaffDTO();
			toDTO(entity, dto);
			dtos.add(dto);
		}
	}
}
