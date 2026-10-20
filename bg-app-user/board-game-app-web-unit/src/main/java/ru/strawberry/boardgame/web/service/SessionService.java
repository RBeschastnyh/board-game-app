package ru.strawberry.boardgame.web.service;

import ru.strawberry.boardgame.web.data.dto.AppSessionDTO;
import ru.strawberry.boardgame.web.data.dto.AuthRequest;

import java.time.LocalDateTime;
import java.util.UUID;

public interface SessionService {

    AppSessionDTO getByLogin(String username);

    AppSessionDTO getById(UUID sessionId);

    LocalDateTime validate(UUID sessionId, String login);

    AppSessionDTO createSession(AuthRequest authRequest);
}
