package nks.abc.depricated.view.converter.user;

import nks.abc.depricated.view.object.objects.user.UserView;
import nks.abc.domain.user.Account;
import nks.abc.domain.user.impl.PersonalInfoImpl;

public class UserViewConvertor {
	
	public static void toDTO(Account entity, UserView dto) {
		dto.setAccountId(entity.getAccountId());
		dto.setEmail(entity.getEmail());
		dto.setPersonalInfoId(entity.getPeronalInfo().getId());
		dto.setBirthday(entity.getPeronalInfo().getBirthday());
		dto.setFirstName(entity.getPeronalInfo().getFirstName());
		dto.setPhoneNum(entity.getPeronalInfo().getPhoneNum());
		dto.setSirName(entity.getPeronalInfo().getSirName());
		dto.setPatronomic(entity.getPeronalInfo().getPatronomic());
		dto.setPasswordHash(entity.getPasswordHash());
//		dto.setIsDisabled(entity.getIsFire());
	}
	
	public static void toEntity(UserView dto, Account entity) {
		entity.setAccountId(dto.getAccountId());
		entity.setEmail(dto.getEmail());
		java.sql.Date sqlDate = null;
		if(dto.getBirthday() != null){
			sqlDate = new java.sql.Date(dto.getBirthday().getTime());
		}
		entity.setPeronalInfo(PersonalInfoImpl.getNew());
		entity.getPeronalInfo().setBirthday(sqlDate);
		entity.getPeronalInfo().setFirstName(dto.getFirstName());
		entity.setAccountId(dto.getAccountId());
		entity.getPeronalInfo().setId(dto.getPersonalInfoId());
		entity.getPeronalInfo().setPhoneNum(dto.getPhoneNum());
		entity.getPeronalInfo().setSirName(dto.getSirName());
		entity.getPeronalInfo().setPatronomic(dto.getPatronomic());
		entity.setPasswordHash(dto.getPasswordHash());
		entity.setIsActive(dto.getIsDisabled());
	}
}
