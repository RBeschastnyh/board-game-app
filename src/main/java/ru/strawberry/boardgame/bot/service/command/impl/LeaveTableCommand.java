package ru.strawberry.boardgame.bot.service.command.impl;

import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethodMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.strawberry.boardgame.bot.service.command.Command;
import ru.strawberry.boardgame.bot.service.command.CommandRequest;
import ru.strawberry.boardgame.repository.PartyRepository;
import ru.strawberry.boardgame.repository.TabletopRepository;
import ru.strawberry.boardgame.repository.dto.Tabletop;
import ru.strawberry.boardgame.repository.impl.PartyRepositoryImpl;
import ru.strawberry.boardgame.repository.impl.TabletopRepositoryImpl;
import ru.strawberry.boardgame.repository.redis.RedisService;
import ru.strawberry.boardgame.repository.redis.RedisUserState;

import java.util.List;
import java.util.Optional;

@Slf4j
public class LeaveTableCommand implements Command {

    private static final List<RedisUserState> GOOD_STATES = List.of(
      RedisUserState.JOINED_TABLE
    );

    private final RedisService redisService = RedisService.getInstance();
    private final TabletopRepository tabletopRepository = new TabletopRepositoryImpl();
    private final PartyRepository partyRepository = new PartyRepositoryImpl();

    @Override
    public BotApiMethodMessage process(CommandRequest command) {
        Long tgId = command.getTgId();
        String message = "Недопустимая команда";

        RedisUserState state = redisService.checkIfExistsState(tgId + "-STATE");

        if (state != null && GOOD_STATES.contains(state)) {
            Optional<Tabletop> tabletop = tabletopRepository.checkIfUserHasTable(tgId);
            if (tabletop.isEmpty()) {
                message = "У вас нет активных столов";
            } else {
                partyRepository.removeUserFromTable(tgId);
                message = String.format("Вы покинули стол %s", tabletop.get().getCode());
            }
        }

        return SendMessage.builder()
                .chatId(tgId)
                .text(message)
                .build();
    }
}
