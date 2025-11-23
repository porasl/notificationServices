package com.porasl.notification.service;

import com.porasl.notification.events.FriendRequestPendingEvent;
import com.porasl.notification.model.Notification;
import com.porasl.notification.repository.NotificationRepository;
import org.springframework.stereotype.Service;

@Service
public class InAppNotificationService {

    private final NotificationRepository repo;

    public InAppNotificationService(NotificationRepository repo) {
        this.repo = repo;
    }

    public void createNotification(FriendRequestPendingEvent event) {
        Notification n = new Notification(
                event.targetUserId(),
                "You have a friend request from " + event.requesterName(),
                event.createdAt()
        );
        repo.save(n);
    }
}
