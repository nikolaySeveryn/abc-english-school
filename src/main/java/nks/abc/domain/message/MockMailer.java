package nks.abc.domain.message;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public class MockMailer implements MailService {

	@Override
	public void sendEmail(Email email) {
		System.out.println("================================================================================");
		System.out.println("                                Sending email:                                  ");
		System.out.println(email);
		System.out.println("================================================================================");
	}
}
