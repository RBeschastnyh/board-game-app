package ru.strawberry.boardgame.web.repository;

import org.jspecify.annotations.NonNull;
import ru.strawberry.boardgame.web.data.entity.AppSession;

import java.util.UUID;

public interface AppSessionRepository {

    AppSession getBySessionId(@NonNull UUID sessionId);

    AppSession getByLogin(@NonNull String username);

    void updateSessionLifetime(UUID sessionId);

    void save(@NonNull AppSession appSession);
}
