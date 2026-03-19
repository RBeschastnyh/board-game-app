package ru.strawberry.boardgame.bot.service.command.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethodMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.strawberry.boardgame.bot.service.command.Command;
import ru.strawberry.boardgame.bot.service.command.CommandRequest;
import ru.strawberry.boardgame.repository.TabletopRepository;
import ru.strawberry.boardgame.repository.dto.Tabletop;
import ru.strawberry.boardgame.repository.PartyRepository;
import ru.strawberry.boardgame.repository.redis.RedisService;
import ru.strawberry.boardgame.repository.redis.RedisUserState;

import java.util.List;
import java.util.Optional;

@Slf4j
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class LeaveTableCommand implements Command {

    private static final List<RedisUserState> GOOD_STATES = List.of(
      RedisUserState.JOINED_TABLE
    );

    private final RedisService redisService;
    private final TabletopRepository tabletopRepository;
    private final PartyRepository partyRepository;

    @Autowired
    public LeaveTableCommand(
            RedisService redisService,
            TabletopRepository tabletopRepository,
            PartyRepository partyRepository
    ) {
        this.redisService = redisService;
        this.tabletopRepository = tabletopRepository;
        this.partyRepository = partyRepository;
    }

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
