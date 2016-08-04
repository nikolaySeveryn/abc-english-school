package nks.abc.bl.service.message;

import org.springframework.stereotype.Component;

@Component
public class MailFactory {
	
	public Email newStaff(String emailAdderss, String password){
		Email email = new Email();
		email.setRecipient(emailAdderss);
		email.setSubject("Вас було зареєстровно в ABC english school");
		email.setBody("Вас було зареєстровно в ABC english school\n"
				+ "Ви можете увійти використовючи:\n"
				+ "Адресу електронноъ пошти: " + emailAdderss + "\n"
				+ "Пароль: " + password + "\n\n"
				+ "Не забудьте змінити пароль на власний коли отримаєте доступ до системи!");
		return email;
	}
}
