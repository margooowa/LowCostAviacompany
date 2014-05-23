package com.lowcost.post;

import java.io.UnsupportedEncodingException;

import javax.annotation.Resource;
import javax.ejb.Singleton;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;
import javax.mail.internet.MimeMultipart;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;



@Singleton
public class PostSender {
	@Resource(name = "mail/mailSession")
	private Session mailSession;
	private Logger logger = Logger.getLogger(getClass().getName());	

	public void sender(String email, String bodyMessage) {
		Message msg = new MimeMessage(mailSession);
		try {
			msg.setSubject("Booking");
			msg.setRecipient(RecipientType.TO, new InternetAddress(
					email));			
			msg.setFrom(new InternetAddress("margooowa91@gmail.com",
					"UKR WINGS"));
			BodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart.setText(bodyMessage);
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(messageBodyPart);
			msg.setContent(multipart);
			Transport.send(msg);			
			logger.info("The letter sent");
		} catch (MessagingException | UnsupportedEncodingException e) {
			logger.error(e);
		}
	}
}
