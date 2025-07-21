package ru.strawberry.boardgame.bot.service.command.impl;

import lombok.extern.slf4j.Slf4j;
import ru.strawberry.boardgame.bot.service.command.Command;
import ru.strawberry.boardgame.bot.service.command.CommandRequest;
import ru.strawberry.boardgame.bot.service.redis.RedisService;
import ru.strawberry.boardgame.bot.service.user.UserService;

import java.util.List;

@Slf4j
public class RegisterTeseraUserCommand implements Command {

    private final UserService userService = UserService.getInstance();
    private final RedisService redisService = RedisService.getInstance();

    private final List<String> inappropriateStates = List.of(
            "REGISTRATION_IN_PROCESS",
            "READY_TO_CHOOSE",
            "CHOOSING"
    );

    @Override
    public void process(CommandRequest command) {
        Long tgId = command.getTgId();
        this.validate(tgId);
        // check if user is already registered
        if (!userService.checkIfTeseraUserIsRegistered(tgId)) {
            log.info("User {} starts tesera user registration", tgId);
            redisService.putState(tgId.toString(), "REGISTRATION_IN_PROCESS");
        }
        // if no - ask for username
    }

    private void validate(Long tgId) {
        String state = redisService.checkIfExistsState(tgId.toString());
        if (state == null || inappropriateStates.contains(state)) {
            log.error("Unsuccessful attempt to register tesera user!");
            throw new RuntimeException("Возникла ошибка или операция недоступна");
        }
    }

    @Override
    public String getMessage() {
        return "Введите имя пользователя";
    }
}
