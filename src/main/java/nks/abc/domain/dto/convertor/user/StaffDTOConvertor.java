package nks.abc.domain.dto.convertor.user;

import java.util.List;

import nks.abc.domain.dto.user.StaffDTO;
import nks.abc.domain.entity.user.Staff;

public class StaffDTOConvertor {
	
	public static void toEntity(StaffDTO dto, Staff entity) {
		UserDTOConvertor.toEntity(dto, entity);
		entity.setPatronomic(dto.getPatronomic());
		entity.isAdministrator(dto.getIsAdministrator());
		entity.isTeacher(dto.getIsTeacher());
	}
	
	public static void toDTO(Staff entity, StaffDTO dto) {
		UserDTOConvertor.toDTO(entity, dto);
		dto.setPatronomic(entity.getPatronomic());
		dto.setIsAdministrator(entity.isAdministrator());
		dto.setIsTeacher(entity.isTeacher());
	}
	
	public static void toEntity(List<StaffDTO> dtos, List<Staff> entities) {
		for(StaffDTO dto : dtos){
			Staff entity = new Staff();
			toEntity(dto, entity);
			entities.add(entity);
		}
	}
	
	public static void toDTO(List<Staff> entities, List<StaffDTO> dtos) {
		for(Staff entity : entities){
			StaffDTO dto = new StaffDTO();
			toDTO(entity, dto);
			dtos.add(dto);
		}
	}
}
