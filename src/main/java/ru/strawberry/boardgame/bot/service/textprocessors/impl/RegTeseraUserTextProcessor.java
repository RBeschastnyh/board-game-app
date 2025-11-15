package ru.strawberry.boardgame.bot.service.textprocessors.impl;

import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethodMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.strawberry.boardgame.bot.service.command.CommandRequest;
import ru.strawberry.boardgame.repository.dto.User;
import ru.strawberry.boardgame.repository.GamesRepository;
import ru.strawberry.boardgame.repository.impl.GamesRepositoryImpl;
import ru.strawberry.boardgame.repository.redis.RedisService;
import ru.strawberry.boardgame.repository.redis.RedisUserState;
import ru.strawberry.boardgame.repository.UserRepository;
import ru.strawberry.boardgame.repository.impl.UserRepositoryImpl;
import ru.strawberry.boardgame.service.tesera.TeseraService;
import ru.strawberry.boardgame.bot.service.textprocessors.TextProcessor;
import ru.strawberry.boardgame.service.tesera.dto.TeseraGame;
import ru.strawberry.boardgame.service.tesera.dto.TeseraUser;

import java.util.List;

/**
 * Saves tesera user, if exists, for telegram user.
 *
 * @author RBeschastnykh
 */
@Slf4j
public class RegTeseraUserTextProcessor implements TextProcessor {

    private final TeseraService teseraService = new TeseraService();
    private final RedisService redisService = RedisService.getInstance();
    private final UserRepository userRepository = new UserRepositoryImpl();
    private final GamesRepository gamesRepository = new GamesRepositoryImpl();

    @Override
    public BotApiMethodMessage process(CommandRequest request) {
        String tgId = request.getTgId().toString();
        User dbUser;
        TeseraUser currentTeseraUser;

        try {
            currentTeseraUser = teseraService.getUserByName(request.getCommand());
            dbUser = userRepository.setTeseraAccountName(request.getTgId(),
                    currentTeseraUser.getUser().getLogin(),
                    currentTeseraUser.getUser().getTeseraId());
        } catch (Exception e) {
            log.error("Error while checking tesera for user: ", e);
            redisService.putState(tgId + "-STATE", RedisUserState.TO_REGISTER);
            throw e;
        }

        long cycles = (currentTeseraUser.getGamesInCollection() / 30) + 1;
        for (int i = 0; i < cycles; i++) {
            List<TeseraGame> listGames = teseraService.getListGames(request.getCommand(), i);
            if (!listGames.isEmpty()) {
                gamesRepository.addGames(listGames, dbUser.getId());
            }
        }

        redisService.putState(tgId + "-STATE", RedisUserState.REGISTRATION_COMPLETE);

        return SendMessage.builder()
                .chatId(tgId)
                .text("Пользователь зарегистрирован! Добавили " + currentTeseraUser.getGamesInCollection() + " коробок в коллекцию")
                .build();
    }
}
