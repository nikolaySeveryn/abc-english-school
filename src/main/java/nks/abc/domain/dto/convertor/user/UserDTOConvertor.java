package nks.abc.domain.dto.convertor.user;

import nks.abc.domain.dto.user.UserDTO;
import nks.abc.domain.entity.user.AccountInfo;

public class UserDTOConvertor {
	
	public static void toDTO(AccountInfo entity, UserDTO dto) {
		dto.setId(entity.getAccountId());
		dto.setLogin(entity.getLogin());
		dto.setBirthday(entity.getBirthday());
		dto.setEmail(entity.getEmail());
		dto.setFirstName(entity.getFirstName());
		dto.setId(entity.getAccountId());
		dto.setPhoneNum(entity.getPhoneNum());
		dto.setSirName(entity.getSirName());
		dto.setPatronomic(entity.getPatronomic());
		dto.setPasswordHash(entity.getPasswordHash());
		dto.setIsDeleted(entity.getIsDeleted());
	}
	
	public static void toEntity(UserDTO dto, AccountInfo entity) {
		entity.setAccountId(dto.getId());
		entity.setLogin(dto.getLogin());
		java.sql.Date sqlDate = null;
		if(dto.getBirthday() != null){
			sqlDate = new java.sql.Date(dto.getBirthday().getTime());
		}
		entity.setBirthday(sqlDate);
		entity.setEmail(dto.getEmail());
		entity.setFirstName(dto.getFirstName());
		entity.setAccountId(dto.getId());
		entity.setPhoneNum(dto.getPhoneNum());
		entity.setSirName(dto.getSirName());
		entity.setPatronomic(dto.getPatronomic());
		entity.setPasswordHash(dto.getPasswordHash());
		entity.setIsDeleted(dto.getIsDeleted());
	}
}
