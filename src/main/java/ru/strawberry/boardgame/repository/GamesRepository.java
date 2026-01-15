package ru.strawberry.boardgame.repository;

import ru.strawberry.boardgame.repository.dto.Games;
import ru.strawberry.boardgame.service.tesera.dto.TeseraGame;

import java.util.List;

public interface GamesRepository {

    void addGames(List<TeseraGame> listGames, Long userTgId);

    List<String> getGamesTitles(Long tgId);
}
