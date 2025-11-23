package com.porasl.notification.model;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
public class Notification {

    @Id @GeneratedValue
    private Long id;

    private Long userId;
    private String message;
    private boolean read = false;
    private Instant createdAt;

    public Notification() {}

    public Notification(Long userId, String message, Instant createdAt) {
        this.userId = userId;
        this.message = message;
        this.createdAt = createdAt;
    }
}

