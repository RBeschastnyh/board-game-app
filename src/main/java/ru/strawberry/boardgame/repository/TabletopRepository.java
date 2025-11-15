package ru.strawberry.boardgame.repository;

import ru.strawberry.boardgame.repository.dto.Tabletop;

import java.util.Optional;

public interface TabletopRepository {

    Optional<Tabletop> checkIfTableExistsByCode(String code);

    void addTable(Long tgId, String code);

    Optional<Tabletop> checkIfUserHasTable(Long tgId);

    void deleteTable(Tabletop tabletop);

    Optional<Tabletop> checkIfUserIsHost(Long tgId);
}
