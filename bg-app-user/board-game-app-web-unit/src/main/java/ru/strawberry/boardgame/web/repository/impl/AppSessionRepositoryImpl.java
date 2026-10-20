package ru.strawberry.boardgame.web.repository.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.strawberry.boardgame.web.data.entity.AppSession;
import ru.strawberry.boardgame.web.data.entity.User;
import ru.strawberry.boardgame.web.exception.DataNotFoundException;
import ru.strawberry.boardgame.web.exception.ExceptionAppCodes;
import ru.strawberry.boardgame.web.repository.AppSessionRepository;

import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Locale;
import java.util.UUID;

@Slf4j
@Repository
@RequiredArgsConstructor
public class AppSessionRepositoryImpl implements AppSessionRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public AppSession getBySessionId(@NonNull UUID sessionId) {
        return jdbcTemplate.query((connection) -> {
            PreparedStatement preparedStatement = connection.prepareStatement("select s.id, s.session_id, s.user_id, " +
                    "u.id, u.email, u.login, u.username, s.valid_from, s.valid_until, s.last_login from sessions s " +
                    "inner join users u on u.id = s.user_id where s.session_id = ?");

            preparedStatement.setString(1, sessionId.toString());

            return preparedStatement;
        }, (resultSet) -> {

            if (!resultSet.first()) {
                log.error("Error while searching session with session id = {}", sessionId);
                throw new DataNotFoundException(ExceptionAppCodes.SESSION_IS_GONE, "Сессия не найдена!");
            }

            User user = resultSet.getString(4) != null ? User.builder()
                    .id(UUID.fromString(resultSet.getString(4)))
                    .email(resultSet.getString(5))
                    .username(resultSet.getString(7))
                    .login(resultSet.getString(6))
                    .build() : null;

            return AppSession.builder()
                    .id(UUID.fromString(resultSet.getString(1)))
                    .sessionId(UUID.fromString(resultSet.getString(2)))
                    .user(user)
                    .validFrom(LocalDateTime.ofInstant(resultSet.getTimestamp(7).toInstant(), ZoneId.of("Europe/Moscow")))
                    .validUntil(LocalDateTime.ofInstant(resultSet.getTimestamp(8).toInstant(), ZoneId.of("Europe/Moscow")))
                    .lastLogin(LocalDateTime.ofInstant(resultSet.getTimestamp(9).toInstant(), ZoneId.of("Europe/Moscow")))
                    .build();
        });
    }

    @Override
    public AppSession getByLogin(@NonNull String login) {
        return jdbcTemplate.query((connection) -> {
            PreparedStatement preparedStatement = connection.prepareStatement("select s.id, s.session_id, s.valid_from, s.valid_until, s.last_login " +
                    "from sessions s inner join users u on u.id = s.user_id where u.login = ?");
            preparedStatement.setString(1, login.toLowerCase(Locale.ROOT));

            return preparedStatement;
        }, (resultSet) -> {
            if (!resultSet.next()) {
                log.info("No session for user {}", login);

                return null;
            }

            return AppSession.builder()
                    .id(UUID.fromString(resultSet.getString(1)))
                    .sessionId(UUID.fromString(resultSet.getString(2)))
                    .validFrom(LocalDateTime.ofInstant(resultSet.getTimestamp(3).toInstant(), ZoneId.of("Eupore/Moscow")))
                    .validUntil(LocalDateTime.ofInstant(resultSet.getTimestamp(4).toInstant(), ZoneId.of("Eupore/Moscow")))
                    .lastLogin(LocalDateTime.ofInstant(resultSet.getTimestamp(5).toInstant(), ZoneId.of("Eupore/Moscow")))
                    .build();
        });
    }

    @Override
    public void updateSessionLifetime(UUID sessionId) {
        jdbcTemplate.update((connection) -> connection.prepareStatement("update sessions s set s.valid_until = s.last_login + interval '1 month'"));
    }

    @Override
    public void save(@NonNull AppSession appSession) {
        jdbcTemplate.update((connection) -> {
           PreparedStatement preparedStatement = connection.prepareStatement("""
                insert into sessions (id, session_id, user_id, valid_from, valid_to, last_login) values (?, ?, ?, ?, ?, ?)
                """);
           int counter = 1;
           preparedStatement.setObject(counter++, appSession.getId());
           preparedStatement.setObject(counter++, appSession.getSessionId());
           preparedStatement.setObject(counter++, appSession.getUser().getId());
           preparedStatement.setTimestamp(counter++, Timestamp.valueOf(appSession.getValidFrom()));
           preparedStatement.setTimestamp(counter++, Timestamp.valueOf(appSession.getValidUntil()));
           preparedStatement.setTimestamp(counter++, Timestamp.valueOf(appSession.getLastLogin()));

           return preparedStatement;
        });
    }
}
