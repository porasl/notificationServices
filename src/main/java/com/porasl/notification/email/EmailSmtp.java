package com.porasl.notification.email;
 
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.porasl.notification.config.Config;
import com.porasl.notification.service.ConfigService;
 
public class EmailSmtp {
 
	  @Autowired
	  private static ConfigService configService;
	  
	  private static final Logger log = LoggerFactory.getLogger(EmailSmtp.class);
       
      public static void sendSimpleGodaddyEmail(List<String> to, EmailEnum type, String token, String username ) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        
              
        configService = (ConfigService) context.getBean("configService");
  	    
        List<Config> configInfos = configService.findAll();
		HashMap configHashMap = new HashMap();
		configInfos.stream().forEach(configInfo -> { 
			configHashMap.put(configInfo.getConfigName(), configInfo.getConfigValue());
         }); 
	    
	    String transportProtocol = (String) configHashMap.get("mail.transport.protocol");
	    String mail_host = (String) configHashMap.get("mail.host");
	    String smtp_auth = (String) configHashMap.get("mail.smtp.auth");
	    String mail_user = (String) configHashMap.get("mail.user");
	    String mail_password = (String) configHashMap.get("mail.password");
	    
        try {
         Properties props = System.getProperties();
         props.setProperty("mail.transport.protocol", transportProtocol);   
         props.setProperty("mail.host", mail_host);      
         props.put("mail.smtp.auth", smtp_auth);
         props.setProperty("mail.user", mail_user);
         props.setProperty("mail.password", mail_password);
         
        Session mailSession = Session.getDefaultInstance(props, null);
      
        Transport transport = mailSession.getTransport("smtp");
        MimeMessage message = new MimeMessage(mailSession);
        message.setSentDate(new java.util.Date());
       
        message.setFrom(new InternetAddress(mail_user));
        for (int i=0;i < to.size();i++)
        {
                                         
         message.addRecipient(Message.RecipientType.TO, new  
          InternetAddress(to.get(i)));
        }
        
        String text ="";
        String subject ="";
        
        switch (type)
        {
          case ACCOUNT_ACTIVATION: {
        	  text = "Your Inrik Account is created. Please activate your account. You can do that ny clicking om ther folllowing link:"
        	  		+ " <a href='http://inrik.com/activate.jsp?activationToken="+ token + "&username="+ username +"'> Activate </a>";
        	  subject = "Activate your INRIK account";
          }
          break;
          
          case FORGOT_PASSWORD:
        	  text = "We received a request to rest your password. Ignore this message if you haven't requested this action."+
        			  "Otherwise. Please navigate to <a href='http://inrik.com/forgot_password. Your token is: "+ token;
        	  subject = "Reset your INRIK password";
        	  break;
        	  
          default:
        }
        message.setText(text);
        message.setSubject(subject);
        
        transport.connect("smtpout.secureserver.net",props.getProperty("mail.user"), props.getProperty("mail.password"));
        transport.sendMessage(message,
         message.getRecipients(Message.RecipientType.TO));
        transport.close();
                       
         log.debug("Email via go daddy sent");
        } catch (Exception e) {
          log.error("Failed to send Email : " + e.getMessage(), e);
        }
   }
        
}