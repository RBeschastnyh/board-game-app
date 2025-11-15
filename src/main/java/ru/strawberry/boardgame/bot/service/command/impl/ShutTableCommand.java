package ru.strawberry.boardgame.bot.service.command.impl;

import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethodMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.strawberry.boardgame.bot.service.command.Command;
import ru.strawberry.boardgame.bot.service.command.CommandRequest;
import ru.strawberry.boardgame.repository.TabletopRepository;
import ru.strawberry.boardgame.repository.dto.Tabletop;
import ru.strawberry.boardgame.repository.impl.TabletopRepositoryImpl;
import ru.strawberry.boardgame.repository.redis.RedisService;
import ru.strawberry.boardgame.repository.redis.RedisUserState;

import java.util.Optional;

@Slf4j
public class ShutTableCommand implements Command {
    private final TabletopRepository tabletopRepository = new TabletopRepositoryImpl();
    private final RedisService redisService = RedisService.getInstance();

    @Override
    public BotApiMethodMessage process(CommandRequest command) {
        Long tgId = command.getTgId();
        Optional<Tabletop> tabletop = tabletopRepository.checkIfUserIsHost(tgId);

        String response;
        SendMessage.SendMessageBuilder builder = SendMessage.builder()
                .chatId(tgId);
        if (tabletop.isEmpty()) {
            response = "У вас нет столов где Вы хост";
        } else {
            redisService.putState(tgId + "-STATE", RedisUserState.READY_TO_CHOOSE_GAME);
            response = String.format("Стол %s закрыт! Можно выбирать игры!", tabletop.get().getCode());
        }

        return builder
                .text(response)
                .build();
    }
}
