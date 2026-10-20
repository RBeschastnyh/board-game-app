package ru.strawberry.boardgame.web.repository;

import org.jspecify.annotations.NonNull;
import ru.strawberry.boardgame.web.data.entity.User;

import java.util.UUID;

public interface UserRepository {

    UUID save(@NonNull User user);

    boolean checkUserExistsByLogin(@NonNull String login);

    User getByLogin(@NonNull String login);
}
