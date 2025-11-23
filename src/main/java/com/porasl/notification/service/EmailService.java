package com.porasl.notification.service;

import com.porasl.notification.events.FriendRequestPendingEvent;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    /**
     * Sends a friend request notification email.
     */
    public void sendFriendRequestEmail(FriendRequestPendingEvent event) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(event.targetEmail());
        message.setSubject("You have a new friend request on Inrik!");
        message.setText(buildEmailBody(event));

        mailSender.send(message);
    }

    /**
     * Builds the email body text.
     */
    private String buildEmailBody(FriendRequestPendingEvent event) {
        return """
                Hello,

                %s has sent you a friend request on Inrik.

                To accept the request:
                1. Open the Inrik app OR
                2. Log in at https://inrik.com and check your notifications.

                If you don't have an account, you can sign up using this email.

                Best,
                Inrik Team
                """.formatted(event.requesterName());
    }
}

