package nks.abc.domain.view.converter.user;

import nks.abc.domain.entity.user.AccountInfo;
import nks.abc.domain.entity.user.PersonalInfo;
import nks.abc.domain.view.object.objects.user.UserView;

public class UserViewConvertor {
	
	public static void toDTO(AccountInfo entity, UserView dto) {
		dto.setAccountId(entity.getAccountId());
		dto.setLogin(entity.getLogin());
		dto.setPersonalInfoId(entity.getPeronalInfo().getId());
		dto.setBirthday(entity.getPeronalInfo().getBirthday());
		dto.setEmail(entity.getPeronalInfo().getEmail());
		dto.setFirstName(entity.getPeronalInfo().getFirstName());
		dto.setPhoneNum(entity.getPeronalInfo().getPhoneNum());
		dto.setSirName(entity.getPeronalInfo().getSirName());
		dto.setPatronomic(entity.getPeronalInfo().getPatronomic());
		dto.setPasswordHash(entity.getPasswordHash());
		dto.setIsDeleted(entity.getIsDeleted());
	}
	
	public static void toEntity(UserView dto, AccountInfo entity) {
		entity.setAccountId(dto.getAccountId());
		entity.setLogin(dto.getLogin());
		java.sql.Date sqlDate = null;
		if(dto.getBirthday() != null){
			sqlDate = new java.sql.Date(dto.getBirthday().getTime());
		}
		entity.setPeronalInfo(PersonalInfo.getNew());
		entity.getPeronalInfo().setBirthday(sqlDate);
		entity.getPeronalInfo().setEmail(dto.getEmail());
		entity.getPeronalInfo().setFirstName(dto.getFirstName());
		entity.setAccountId(dto.getAccountId());
		entity.getPeronalInfo().setId(dto.getPersonalInfoId());
		entity.getPeronalInfo().setPhoneNum(dto.getPhoneNum());
		entity.getPeronalInfo().setSirName(dto.getSirName());
		entity.getPeronalInfo().setPatronomic(dto.getPatronomic());
		entity.setPasswordHash(dto.getPasswordHash());
		entity.setIsDeleted(dto.getIsDeleted());
	}
}
