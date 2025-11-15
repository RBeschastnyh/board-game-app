package ru.strawberry.boardgame.repository;

public interface PartyRepository {

    void addUserToTable(Long tgIg, Long tableId);

    void removeUserFromTable(Long tgId);
}
