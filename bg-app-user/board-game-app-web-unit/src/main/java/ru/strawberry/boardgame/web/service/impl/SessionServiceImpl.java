package ru.strawberry.boardgame.web.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.strawberry.boardgame.web.data.dto.AppSessionDTO;
import ru.strawberry.boardgame.web.data.dto.AuthRequest;
import ru.strawberry.boardgame.web.data.entity.AppSession;
import ru.strawberry.boardgame.web.data.entity.User;
import ru.strawberry.boardgame.web.exception.DataNotFoundException;
import ru.strawberry.boardgame.web.exception.ExceptionAppCodes;
import ru.strawberry.boardgame.web.exception.StaleDataException;
import ru.strawberry.boardgame.web.repository.AppSessionRepository;
import ru.strawberry.boardgame.web.repository.UserRepository;
import ru.strawberry.boardgame.web.service.SessionService;
import ru.strawberry.boardgame.web.utils.AppSessionDTOConverter;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class SessionServiceImpl implements SessionService {

    private final AppSessionRepository appSessionRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AppSessionDTO getByLogin(String login) {
        if (login == null) {
            log.error("Trying request session by null user name!");

            throw new IllegalArgumentException("Неверный идентификатор сессии");
        }

        if (!userRepository.checkUserExistsByLogin(login)) {
            log.error("Suspicious attempt to get session for user {} which is not registered", login);
        }

        AppSession session = appSessionRepository.getByLogin(login);

        if (session == null) {
            log.info("Establishing first session for user {}", login);
        } else {

        }

        return AppSessionDTOConverter.fromEntity(session);
    }

    @Override
    public AppSessionDTO getById(UUID sessionId) {
        if (sessionId == null) {
            log.error("Trying request session by null session id!");

            throw new IllegalArgumentException("Неверный идентификатор сессии");
        }

        AppSession session = appSessionRepository.getBySessionId(sessionId);

        return AppSessionDTOConverter.fromEntity(session);
    }

    @Override
    public LocalDateTime validate(UUID sessionId, String login) {
        if (sessionId == null) {
            log.error("Trying request session by null session id!");

            throw new IllegalArgumentException("Неверный идентификатор сессии");
        }

        if (login == null) {
            log.error("Trying request session by null user login!");

            throw new IllegalArgumentException("Неверный логин");
        }

        if (!userRepository.checkUserExistsByLogin(login)) {
            log.error("User with login {} does not exist", login);

            throw new DataNotFoundException(ExceptionAppCodes.USER_IS_GONE, "Пользователь не существует!");
        }

        AppSession session = appSessionRepository.getBySessionId(sessionId);

        if (session == null) {
            log.error("Requested session that no longer exists!");

            throw new DataNotFoundException(ExceptionAppCodes.SESSION_IS_GONE, "Сессия не существует!");
        }

        if (LocalDateTime.now(ZoneId.systemDefault()).isAfter(session.getValidUntil())) {
            log.info("Session {} is stale!", sessionId);

            throw new StaleDataException(ExceptionAppCodes.LOG_OUT, "Сессия не существует!");
        }

        appSessionRepository.updateSessionLifetime(sessionId);

        return session.getLastLogin().plusMonths(1L);
    }

    @Override
    public AppSessionDTO createSession(AuthRequest authRequest) {
        if (!userRepository.checkUserExistsByLogin(authRequest.getLogin())) {
            log.error("No user {}", authRequest.getLogin());

            throw new DataNotFoundException(ExceptionAppCodes.MALFORMED_DATA, "Неверный логин");
        }

        User user = userRepository.getByLogin(authRequest.getLogin());

        if (passwordEncoder.matches(authRequest.getPassword(), user.getPassword())) {
            log.info("Successful login for user {}", authRequest.getLogin());
            LocalDateTime now = LocalDateTime.now(ZoneId.systemDefault());

            AppSession appSession = AppSession.builder()
                    .id(UUID.randomUUID())
                    .sessionId(UUID.randomUUID())
                    .user(user)
                    .validFrom(now)
                    .validUntil(now.plusMonths(1))
                    .lastLogin(now)
                    .build();

            appSessionRepository.save(appSession);

            return AppSessionDTOConverter.fromEntity(appSession);
        }

        throw new IllegalStateException();
    }
}
