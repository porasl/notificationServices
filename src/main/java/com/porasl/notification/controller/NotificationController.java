package com.porasl.notification.controller;

import com.porasl.notification.model.Notification;
import com.porasl.notification.repository.NotificationRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    private final NotificationRepository repo;

    public NotificationController(NotificationRepository repo) {
        this.repo = repo;
    }

    @GetMapping("/{userId}")
    public List<Notification> getNotifications(@PathVariable Long userId) {
        return repo.findByUserIdOrderByCreatedAtDesc(userId);
    }
}
