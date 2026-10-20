package ru.strawberry.boardgame.web.data.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
@ToString
@AllArgsConstructor
public class AppSessionDTO {

    private UUID id;
    private UUID sessionId;
    private UUID userId;
    private LocalDateTime validFrom;
    private LocalDateTime validUntil;
    private LocalDateTime lastLogin;
}
