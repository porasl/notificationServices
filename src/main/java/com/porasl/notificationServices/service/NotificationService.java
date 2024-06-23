package com.porasl.notificationServices.service;

import com.porasl.notificationServices.model.NotificationMessage;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    public NotificationMessage sendNotification(NotificationMessage message) {
        return new NotificationMessage("Message from porasl.com, " + message.getContent() + "!");
    }
}
