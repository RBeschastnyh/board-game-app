package ru.strawberry.boardgame.bot.service.command.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethodMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.strawberry.boardgame.exceptions.InvalidActionException;
import ru.strawberry.boardgame.bot.service.command.Command;
import ru.strawberry.boardgame.bot.service.command.CommandRequest;
import ru.strawberry.boardgame.repository.redis.RedisService;
import ru.strawberry.boardgame.repository.redis.RedisUserState;
import ru.strawberry.boardgame.service.UserService;

import java.util.List;

@Slf4j
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class RegisterTeseraUserCommand implements Command {

    private final UserService userService;
    private final RedisService redisService;

    private final List<RedisUserState> inappropriateStates = List.of(
            RedisUserState.REGISTRATION_IN_PROCESS,
            RedisUserState.REGISTRATION_COMPLETE,
            RedisUserState.JOINING_TABLE,
            RedisUserState.JOINED_TABLE
    );

    @Autowired
    public RegisterTeseraUserCommand(UserService userService, RedisService redisService) {
        this.userService = userService;
        this.redisService = redisService;
    }

    @Override
    public BotApiMethodMessage process(CommandRequest command) {
        Long tgId = command.getTgId();
        String message = "Введите имя пользователя!";

        RedisUserState state = redisService.checkIfExistsState(tgId + "-STATE");
        if (state == null || inappropriateStates.contains(state)) {
            log.error("Unsuccessful attempt to register tesera user!");
            throw new InvalidActionException("Возникла ошибка или операция недоступна");
        }

        // check if user is already registered
        if (!userService.checkIfTeseraUserIsRegistered(tgId)) {
            log.info("User {} starts tesera user registration", tgId);
            redisService.putState(tgId + "-STATE", RedisUserState.REGISTRATION_IN_PROCESS);
        } else {
            message = "Пользователь уже зарегистрирован!";
        }

        return SendMessage.builder()
                .chatId(tgId)
                .text(message)
                .build();
    }
}
