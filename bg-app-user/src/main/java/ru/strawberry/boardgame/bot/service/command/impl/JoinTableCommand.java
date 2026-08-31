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
import ru.strawberry.boardgame.repository.dto.Tabletop;
import ru.strawberry.boardgame.repository.redis.RedisService;
import ru.strawberry.boardgame.repository.redis.RedisUserState;
import ru.strawberry.boardgame.repository.TabletopRepository;

import java.util.List;
import java.util.Optional;

@Slf4j
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class JoinTableCommand implements Command {
    private static final List<RedisUserState> GOOD_STATES = List.of(
            RedisUserState.TO_REGISTER,
            RedisUserState.REGISTRATION_COMPLETE,
            RedisUserState.JOINING_TABLE,
            RedisUserState.JOINED_TABLE
    );

    private final RedisService redisService;
    private final TabletopRepository tabletopRepository;

    @Autowired
    public JoinTableCommand(RedisService redisService, TabletopRepository tabletopRepository) {
        this.redisService = redisService;
        this.tabletopRepository = tabletopRepository;
    }

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
