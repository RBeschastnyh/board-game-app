package ru.strawberry.boardgame.bot.factory;

import lombok.extern.slf4j.Slf4j;
import ru.strawberry.boardgame.bot.service.command.impl.*;
import ru.strawberry.boardgame.exceptions.UserInputException;
import ru.strawberry.boardgame.bot.service.command.Command;

import javax.validation.constraints.NotNull;

@Slf4j
public class CommandFactory {

    public Command getCommand(@NotNull String text) {
        switch (text) {
            case "start" -> {
                log.info("Requested start command");
                return new StartCommand(text);
            }
            case "regteserauser" -> {
                log.info("Requested register tesera user command");
                return new RegisterTeseraUserCommand();
            }
            case "create" -> {
                log.info("Requested create table command");
                return new CreateTableCommand();
            }
            case "close" -> {
                log.info("Requested close table command");
                return new CloseTableCommand();
            }
            case "join" -> {
                log.info("Requested join table command");
                return new JoinTableCommand();
            }
            case "shut" -> {
                log.info("Requested shut table command");
                return new ShutTableCommand();
            }
            case "leave" -> {
                log.info("Requested leave table command");
                return new LeaveTableCommand();
            }
            case "choose" -> {
                log.info("Requested choose games command");
                return new ChooseGameCommand();
            }
        }

        throw new UserInputException("Введённой команды не существует!");
    }
}
