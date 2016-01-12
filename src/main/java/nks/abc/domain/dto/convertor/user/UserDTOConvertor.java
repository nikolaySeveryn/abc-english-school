package nks.abc.domain.dto.convertor.user;

import nks.abc.domain.dto.user.UserDTO;
import nks.abc.domain.entity.user.User;

public class UserDTOConvertor {
	
	public static void toDTO(User entity, UserDTO dto) {
		dto.setId(entity.getUserId());
		dto.setLogin(entity.getLogin());
		dto.setBirthday(entity.getBirthday());
		dto.setEmail(entity.getEmail());
		dto.setFirstName(entity.getFirstName());
		dto.setId(entity.getUserId());
		dto.setPhoneNum(entity.getPhoneNum());
		dto.setRole(entity.getRole());
		dto.setSirName(entity.getSirName());
		dto.setPasswordHash(entity.getPasswordHash());
	}
	
	public static void toEntity(UserDTO dto, User entity) {
		entity.setUserId(dto.getId());
		entity.setLogin(dto.getLogin());
		java.sql.Date sqlDate = null;
		if(dto.getBirthday() != null){
			sqlDate = new java.sql.Date(dto.getBirthday().getTime());
		}
		entity.setBirthday(sqlDate);
		entity.setEmail(dto.getEmail());
		entity.setFirstName(dto.getFirstName());
		entity.setUserId(dto.getId());
		entity.setPhoneNum(dto.getPhoneNum());
		entity.setRole(dto.getRole());
		entity.setSirName(dto.getSirName());
		entity.setPasswordHash(dto.getPasswordHash());
	}
}
