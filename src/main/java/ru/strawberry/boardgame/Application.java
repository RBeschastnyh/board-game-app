package ru.strawberry.boardgame;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ru.strawberry.boardgame.bot.BoardGameBot;
import ru.strawberry.boardgame.bot.util.EnvVars;


/**
 * Main class.
 *
 * @author RBeschastnykh
 */
public class Application {

    public static void main(String[] args) throws TelegramApiException {
        String botToken = System.getenv(EnvVars.BOT_TOKEN.name());

        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        telegramBotsApi.registerBot(new BoardGameBot(botToken));
    }
}
