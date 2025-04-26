package com.porasl.notificationServices.email;

import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.Message.RecipientType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.porasl.notificationServices.config.Config;
import com.porasl.notificationServices.service.ConfigService;

public class EmailExchange {

	@Autowired
	private static ConfigService configService;

	private static final Logger log = LoggerFactory.getLogger(EmailExchange.class);

	public static void ExchangeEmail(EmailEnum type, String token, String username) {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

		configService = (ConfigService) context.getBean("configService");

		List<Config> configInfos = configService.findAll();
		HashMap configHashMap = new HashMap();
		configInfos.stream().forEach(configInfo -> {
			configHashMap.put(configInfo.getConfigName(), configInfo.getConfigValue());
		});

		// exchange email properties
		String smtp_auth = (String) configHashMap.get("mail.smtp.auth"); // true
		String mail_sender = (String) configHashMap.get("mail.sender");
		String mail_smtp_host = (String) configHashMap.get("mail.smtp.host");// outlook.office365.com
		String mail_smtp_starttls_enable = (String) configHashMap.get("mail.smtp.starttls.enable");// true
		String mail_smtp_port = (String) configHashMap.get("mail.smtp.port");// 587
		String mail_password = (String) configHashMap.get("mail.password");

		try {
			Properties props = System.getProperties();
			props.put("mail.smtp.auth", smtp_auth);
			props.put("mail.smtp.starttls.enable", mail_smtp_starttls_enable);
			props.put("mail.smtp.host", mail_smtp_host);
			props.put("mail.smtp.port", mail_smtp_port);
			props.setProperty("mail.password", mail_password);
			props.setProperty("mail.sender", mail_sender);
			props.put("mail.smtp.starttls.enable", "true"); //enable STARTTLS

			// creating session
			Session session = Session.getInstance(props, new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(mail_sender, mail_password);
				}
			});
			
			String emailBody = "";
			String subject = "";

			switch (type) {
			case ACCOUNT_ACTIVATION: {
				emailBody = "Your Inrik Account is created. Please activate your account. You can do that ny clicking om ther folllowing link:"
						+ " <a href='https://inrik.com/activate.jsp?activationToken=" + token + "&username=" + username
						+ "'> Activate </a>";
				subject = "Activate your INRIK account";
			}
				break;

			case FORGOT_PASSWORD:
				emailBody = "We received a request to rest your password. Ignore this message if you haven't requested this action."
						+ "Otherwise. Please navigate to <a href='https://inrik.com/forgot_password. Your token is: "
						+ token;
				subject = "Reset your INRIK password";
				break;

			default:
			}
			
			// Create a default MimeMessage object
			Message message = new MimeMessage(session);
			message.setSentDate(new java.util.Date());
			message.setText(emailBody);
			message.setSubject(subject);
			message.setFrom(new InternetAddress(mail_sender));
			message.addRecipient(RecipientType.TO, new InternetAddress(username));
			
			// setting HTML message body
			//mimeMessage.setContent(EmailBody,"text/html; charset=utf-8");
			
			Transport.send(message);

			log.info("Email via Exchange server is sent");
		} catch (Exception e) {
			log.error("Failed to send Email : " + e.getMessage(), e);
		}
	}

}