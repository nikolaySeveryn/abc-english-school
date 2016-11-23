package nks.abc.domain.message;

import org.springframework.stereotype.Service;


import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

@Service
public class SpringMailer implements MailService {
	
	private final static Logger log = Logger.getLogger(SpringMailer.class);

	@Autowired
	private MailSender mailSender;
	
	public void setMailSender(MailSender mailSender){
		this.mailSender = mailSender;
	}
	
	@Override
	public void sendEmail(Email email) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(email.getRecipient());
		message.setSubject(email.getSubject());
		message.setText(email.getBody());
		mailSender.send(message);
		log.info("Sent email:\n" + email);
	}

}
