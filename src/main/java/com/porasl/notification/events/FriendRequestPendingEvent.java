package com.porasl.notification.events;

import java.time.Instant;

public record FriendRequestPendingEvent(
        Long requesterId,
        String requesterName,
        Long targetUserId,
        String targetEmail,
        Instant createdAt
) {}
