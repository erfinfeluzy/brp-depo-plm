package com.depobrp.integration.email;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-ctx-test.xml")
public class EmailSenderTest {

	@Autowired
	@Qualifier("emailSender")
	private EmailSender emailSender;
	
	@Test
	public void sendEmailTest(){
		
		try {
			emailSender.sendMail(
					"erfin", 
					"erfin.feluzy@erfinfeluzy.com", 
					"test sending email", 
					"Hi erfin apa kabar");
			
		} catch (Exception e) {
			e.printStackTrace();
			Assert.assertNull(e);
		}
		
	}
	
}
