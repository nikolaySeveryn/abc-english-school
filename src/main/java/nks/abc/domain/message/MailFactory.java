package nks.abc.domain.message;

import org.springframework.stereotype.Component;

@Component
public class MailFactory {
	//TODO:move to some xml
	
	public Email newStaff(String emailAddress, String password) {
		Email email = new Email();
		email.setRecipient(emailAddress);
		email.setSubject("Вас було зареєстровно в ABC english school");
		email.setBody("Вас було зареєстровно в ABC english school\n"
				+ "Ви можете увійти використавши:\n"
				+ "Адресу електронноъ пошти: " + emailAddress + "\n"
				+ "Пароль: " + password + "\n\n"
				+ "Не забудьте змінити пароль на власний коли отримаєте доступ до системи!");
		return email;
	}
	
	public Email newStudent(String emailAddress, String password) {
		Email email = new Email();
		email.setRecipient(emailAddress);
		email.setSubject("Вас було зареєстровно в ABC english school");
		email.setBody("Вас було зареєстровно в ABC english school\n"
				+ "Ви можете увійти використавши:\n"
				+ "Адресу електронноъ пошти: " + emailAddress + "\n"
				+ "Пароль: " + password + "\n\n"
				+ "Не забудьте змінити пароль на власний коли отримаєте доступ до системи!");
		return email;
	}
}
