package ru.strawberry.boardgame.repository.impl;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.annotations.QueryHints;
import ru.strawberry.boardgame.exceptions.TeseraGamesProcessingException;
import ru.strawberry.boardgame.repository.dto.Games;
import ru.strawberry.boardgame.repository.GamesRepository;
import ru.strawberry.boardgame.service.tesera.dto.TeseraGame;
import ru.strawberry.boardgame.sesion.SessionContextImpl;

import java.util.List;

@Slf4j
public class GamesRepositoryImpl implements GamesRepository {

    @Override
    public void addGames(List<TeseraGame> listGames, Long userTgId) {
        if (!listGames.isEmpty()) {
            log.trace("List of games is not empty for {}", userTgId);
            List<Games> list = listGames.stream()
                    .map(this::createFromTeseraGame)
                    .toList();
            log.trace("Setting user id for games");
            list.forEach(g -> g.setUserId(userTgId));

            log.trace("Start writing games for user {}", userTgId);
            Transaction transaction = null;
            try (Session session = SessionContextImpl.getInstance().getSession()) {
                log.trace("Begin transaction");
                transaction = session.beginTransaction();

                list.forEach(session::persist);
                session.flush();
                transaction.commit();
                log.trace("End transaction");
            } catch (Exception e) {
                log.error("Error while persisting games!", e);
                if (transaction != null) {
                    transaction.rollback();
                }
                throw new TeseraGamesProcessingException("Возникла ошибка при загрузке игр, попробуйте позже");
            }
        }
    }

    @Override
    public List<Games> getGames(Long tgId) {
        Transaction transaction = null;
        try (Session session = SessionContextImpl.getInstance().getSession()) {
            transaction = session.beginTransaction();
            session.createNativeQuery("""
                    with players_count as (
                        select count(1) from Party p join Tabletop t on p.tableId = t.id
                         join User u on p.userId = u.id
                         where u.tgId = :id
                    )
                    select g from Games g join User u on g.userId = u.id
                     where u.tgId = :id and (g.playersMin is null or g.playersMin >= players_count)
                     and (g.playersMax is null or g.playersMax <= players_count)
                     and g.isAddition = false
                     limit 5
                    """, Games.class)
                    .setHint(QueryHints.READ_ONLY, true)
                    .setParameter("id", tgId)
                    .getResultList();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
        return null;
    }

    private Games createFromTeseraGame(TeseraGame teseraGame) {
        return Games.builder()
                .teseraId(teseraGame.getTeseraId())
                .ratingMy(teseraGame.getRatingMy())
                .teseraUrl(teseraGame.getTeseraUrl())
                .title(teseraGame.getTitle())
                .alias(teseraGame.getAlias())
                .isAddition(teseraGame.getIsAddition())
                .build();
    }
}
