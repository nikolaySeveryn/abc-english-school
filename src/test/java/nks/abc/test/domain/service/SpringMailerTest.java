package nks.abc.test.domain.service;

import static org.junit.Assert.*;


import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import nks.abc.domain.message.Email;
import nks.abc.domain.message.SpringMailer;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:WEB-INF/testApplicationContext.xml" }) 
public class SpringMailerTest {

	@Test
	@Ignore
	public void test() {
		GenericApplicationContext ctx = new GenericApplicationContext();
		XmlBeanDefinitionReader xmlReader = new XmlBeanDefinitionReader(ctx);
		xmlReader.loadBeanDefinitions(new ClassPathResource("WEB-INF/testApplicationContext.xml"));
		ctx.refresh();

		SpringMailer mailer = (SpringMailer) ctx.getBean("mailService");
		mailer.sendEmail(new Email("", "test", "test body"));
	}
}
