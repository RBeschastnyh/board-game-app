package ru.strawberry.boardgame.bot.service.user.repository;

public interface UserRepository {

    boolean checkIfUserExists(Long tgId);

    void createUser(Long tgId);
}
