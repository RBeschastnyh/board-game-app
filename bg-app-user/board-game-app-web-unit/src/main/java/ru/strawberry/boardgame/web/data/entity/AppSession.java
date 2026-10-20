package ru.strawberry.boardgame.web.data.entity;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AppSession {

    private UUID id = UUID.randomUUID();
    private UUID sessionId;
    private User user;
    private LocalDateTime validFrom;
    private LocalDateTime validUntil;
    private LocalDateTime lastLogin;
}
