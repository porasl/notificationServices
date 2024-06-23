package com.porasl.notificationServices.controller;

import com.example.model.NotificationMessage;
import com.example.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @MessageMapping("/send")
    @SendTo("/topic/notifications")
    public NotificationMessage send(NotificationMessage message) {
        return notificationService.sendNotification(message);
    }
}
