package ru.strawberry.boardgame.web.utils;

import ru.strawberry.boardgame.web.data.dto.AppSessionDTO;
import ru.strawberry.boardgame.web.data.entity.AppSession;
import ru.strawberry.boardgame.web.data.entity.User;

import java.util.Optional;

public class AppSessionDTOConverter {

    public static AppSessionDTO fromEntity(AppSession appSession) {
        return AppSessionDTO.builder()
                .sessionId(appSession.getSessionId())
                .userId(Optional.ofNullable(appSession.getUser()).map(User::getId).orElse(null))
                .validFrom(appSession.getValidFrom())
                .validUntil(appSession.getValidUntil())
                .lastLogin(appSession.getLastLogin())
                .build();
    }
}
