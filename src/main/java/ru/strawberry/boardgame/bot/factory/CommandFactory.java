package ru.strawberry.boardgame.bot.factory;

import lombok.extern.slf4j.Slf4j;
import org.jvnet.hk2.annotations.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;
import ru.strawberry.boardgame.bot.service.command.impl.*;
import ru.strawberry.boardgame.config.ApplicationConfig;
import ru.strawberry.boardgame.exceptions.UserInputException;
import ru.strawberry.boardgame.bot.service.command.Command;

import javax.validation.constraints.NotNull;

@Slf4j
@Component
public class CommandFactory {

    public Command getCommand(@NotNull String text) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(ApplicationConfig.class);
        return switch (text) {
            case "start" -> {
                log.info("Requested start command");
                yield applicationContext.getBean(StartCommand.class);
            }
            case "regteserauser" -> {
                log.info("Requested register tesera user command");
                yield applicationContext.getBean(RegisterTeseraUserCommand.class);
            }
            case "create" -> {
                log.info("Requested create table command");
                yield applicationContext.getBean(CreateTableCommand.class);
            }
            case "close" -> {
                log.info("Requested close table command");
                yield applicationContext.getBean(CloseTableCommand.class);
            }
            case "join" -> {
                log.info("Requested join table command");
                yield applicationContext.getBean(JoinTableCommand.class);
            }
            case "shut" -> {
                log.info("Requested shut table command");
                yield applicationContext.getBean(ShutTableCommand.class);
            }
            case "leave" -> {
                log.info("Requested leave table command");
                yield applicationContext.getBean(LeaveTableCommand.class);
            }
            case "choose" -> {
                log.info("Requested choose games command");
                yield applicationContext.getBean(ChooseGameCommand.class);
            }
            default -> throw new UserInputException("Введённой команды не существует!");
        };
    }
}
