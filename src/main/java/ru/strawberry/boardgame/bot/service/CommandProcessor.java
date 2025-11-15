package ru.strawberry.boardgame.bot.service;

import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethodMessage;
import ru.strawberry.boardgame.exceptions.UserInputException;
import ru.strawberry.boardgame.bot.service.command.Command;
import ru.strawberry.boardgame.bot.service.command.CommandRequest;
import ru.strawberry.boardgame.bot.factory.CommandFactory;

@Slf4j
public class CommandProcessor {

    private final CommandFactory commandFactory = new CommandFactory();

    public BotApiMethodMessage process(CommandRequest command) {
        if (command == null) {
            log.error("Command can not be null!");
            throw new UserInputException("Некорректный ввод!");
        }
        Command userCommand = commandFactory.getCommand(command.getCommand());
        return userCommand.process(command);
    }
}
