package ru.strawberry.boardgame.bot.service;

import lombok.extern.slf4j.Slf4j;
import ru.strawberry.boardgame.bot.service.command.Command;
import ru.strawberry.boardgame.bot.service.command.factory.CommandFactory;

@Slf4j
public class CommandProcessor {

    private final CommandFactory commandFactory = new CommandFactory();

    public String process(String command) {
        if (command == null) {
            log.error("Command can not be null!");
            throw new IllegalArgumentException("Некорректный ввод!");
        }
        Command userCommand = commandFactory.getCommand(command);
        userCommand.validate();
        userCommand.process();
        return userCommand.getMessage();
    }
}
