package ru.strawberry.boardgame.bot.service.command.impl;

import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethodMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.strawberry.boardgame.bot.service.command.Command;
import ru.strawberry.boardgame.bot.service.command.CommandRequest;
import ru.strawberry.boardgame.repository.dto.Tabletop;
import ru.strawberry.boardgame.repository.redis.RedisService;
import ru.strawberry.boardgame.repository.redis.RedisUserState;
import ru.strawberry.boardgame.repository.TabletopRepository;
import ru.strawberry.boardgame.repository.impl.TabletopRepositoryImpl;

import java.util.List;
import java.util.Optional;

@Slf4j
public class JoinTableCommand implements Command {
    private static final List<RedisUserState> GOOD_STATES = List.of(
            RedisUserState.TO_REGISTER,
            RedisUserState.REGISTRATION_COMPLETE,
            RedisUserState.JOINING_TABLE,
            RedisUserState.JOINED_TABLE
    );

    private final RedisService redisService = RedisService.getInstance();
    private final TabletopRepository tabletopRepository = new TabletopRepositoryImpl();

    @Override
    public BotApiMethodMessage process(CommandRequest command) {
        Long tgIg = command.getTgId();
        String message = "Введите идентификатор стола, к которому хотите присоединиться!";
        RedisUserState state = redisService.checkIfExistsState(tgIg + "-STATE");

        if (state != null && GOOD_STATES.contains(state)) {
            redisService.putState(tgIg + "-STATE", RedisUserState.JOINING_TABLE);

            Optional<Tabletop> tabletop = tabletopRepository.checkIfUserIsHost(command.getTgId());
            if (tabletop.isPresent()) {
                message = String.format("Вы уже являетесь хостом стола %s, поэтому не можете присоединиться к другому столу", tabletop.get().getCode());
            } else {
                tabletop = tabletopRepository.checkIfUserHasTable(command.getTgId());
                if (tabletop.isPresent()) {
                    message = String.format("Вы не можете присоединиться к столу, так как являетесь участником стола %s. Отключитесь от него и попробуйте ещё раз", tabletop.get().getCode());
                }
            }
        } else {
            message = "Недопустимое действие";
        }

        return SendMessage.builder()
                .chatId(tgIg)
                .text(message)
                .build();
    }
}
