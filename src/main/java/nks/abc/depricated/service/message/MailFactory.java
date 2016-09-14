package nks.abc.depricated.service.message;

import org.springframework.stereotype.Component;

@Component
public class MailFactory {
	
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
