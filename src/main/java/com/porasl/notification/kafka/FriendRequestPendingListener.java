package com.porasl.notification.kafka;


import com.porasl.notification.events.FriendRequestPendingEvent;
import com.porasl.notification.service.EmailService;
import com.porasl.notification.service.PushService;
import com.porasl.notification.service.InAppNotificationService;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class FriendRequestPendingListener {

    private final EmailService emailService;
    private final PushService pushService;
    private final InAppNotificationService inAppService;

    public FriendRequestPendingListener(
            EmailService emailService,
            PushService pushService,
            InAppNotificationService inAppService) {

        this.emailService = emailService;
        this.pushService = pushService;
        this.inAppService = inAppService;
    }

    @KafkaListener(topics = "friendRequestPending", groupId = "notification-service")
    public void handleFriendRequestPending(FriendRequestPendingEvent event) {

        // Send email to user
        emailService.sendFriendRequestEmail(event);

        // Push/Websocket notification
        pushService.sendFriendRequestPush(event);

        // In-app UI popup
        inAppService.createNotification(event);
    }
}

