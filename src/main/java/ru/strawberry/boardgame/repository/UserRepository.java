package ru.strawberry.boardgame.repository;

import ru.strawberry.boardgame.repository.dto.User;

import java.util.Optional;

/**
 * Repository for entity type 'User'.
 *
 * @author RBeschastnykh
 */
public interface UserRepository {

    boolean checkIfUserExists(Long tgId);

    void createUser(Long tgId);

    boolean checkIfTeseraUserIsRegistered(Long tgId);

    User setTeseraAccountName(Long tgId, String login, Long teseraId);

    Optional<User> getByTgId(Long tgId);
}
