package com.porasl.notificationServices.service;

import com.example.model.NotificationMessage;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    public NotificationMessage sendNotification(NotificationMessage message) {
        return new NotificationMessage("Hello, " + message.getContent() + "!");
    }
}
