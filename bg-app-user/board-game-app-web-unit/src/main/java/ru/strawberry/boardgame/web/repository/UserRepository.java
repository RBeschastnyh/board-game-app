package ru.strawberry.boardgame.web.repository;

import ru.strawberry.boardgame.web.data.entity.User;

import java.util.UUID;

public interface UserRepository {

    UUID save(User user);
}
