package com.porasl.notification.service;

import com.porasl.notification.events.FriendRequestPendingEvent;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class PushService {

    private final SimpMessagingTemplate messaging;

    public PushService(SimpMessagingTemplate messaging) {
        this.messaging = messaging;
    }

    public void sendFriendRequestPush(FriendRequestPendingEvent event) {
        messaging.convertAndSend(
                "/topic/user/" + event.targetUserId(),
                "You have a new friend request from " + event.requesterName()
        );
    }
}
