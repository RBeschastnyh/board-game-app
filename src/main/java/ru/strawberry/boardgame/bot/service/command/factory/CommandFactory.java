package ru.strawberry.boardgame.bot.service.command.factory;

import lombok.extern.slf4j.Slf4j;
import ru.strawberry.boardgame.bot.service.command.Command;
import ru.strawberry.boardgame.bot.service.command.impl.StartCommand;

import javax.validation.constraints.NotNull;

@Slf4j
public class CommandFactory {

    public Command getCommand(@NotNull String text) {
        if (text.contains("start")) {
            log.info("Requested start command");
            return new StartCommand(text);
        }

        throw new IllegalArgumentException("");
    }
}
