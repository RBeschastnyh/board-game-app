package ru.strawberry.boardgame;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ru.strawberry.boardgame.bot.BoardGameBot;
import ru.strawberry.boardgame.bot.util.EnvVars;
import ru.strawberry.boardgame.config.ApplicationConfig;


/**
 * Main class.
 *
 * @author RBeschastnykh
 */
public class Application {

    public static void main(String[] args) throws TelegramApiException {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(ApplicationConfig.class);

        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        telegramBotsApi.registerBot(applicationContext.getBean(BoardGameBot.class));
    }
}
