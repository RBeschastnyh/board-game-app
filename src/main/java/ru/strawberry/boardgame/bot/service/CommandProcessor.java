package ru.strawberry.boardgame.bot.service;

import lombok.extern.slf4j.Slf4j;
import ru.strawberry.boardgame.bot.service.command.Command;
import ru.strawberry.boardgame.bot.service.command.CommandRequest;
import ru.strawberry.boardgame.bot.service.command.factory.CommandFactory;

@Slf4j
public class CommandProcessor {

    private final CommandFactory commandFactory = new CommandFactory();

    public String process(CommandRequest command) {
        if (command == null) {
            log.error("Command can not be null!");
            throw new IllegalArgumentException("Некорректный ввод!");
        }
        Command userCommand = commandFactory.getCommand(command.getCommand());
        userCommand.process(command);
        return userCommand.getMessage();
    }
}
