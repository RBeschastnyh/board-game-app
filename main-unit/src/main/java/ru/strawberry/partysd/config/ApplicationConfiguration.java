package ru.strawberry.partysd.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ru.strawberry.partysd.bot.Bot;
import ru.strawberry.tesera.config.BoardGameUnitConfiguration;
import ru.strawberry.tesera.service.TeseraService;

/**
 * Application main configuration.
 *
 * @author RBeschastnykh
 */
@Configuration
@Import({BoardGameUnitConfiguration.class})
@PropertySource("classpath:application.properties")
public class ApplicationConfiguration {

    @Autowired private TeseraService teseraService;

    @Value("${bot.token}")
    private String botToken;

    @Bean
    public TelegramBotsApi telegramBotsApi() {
        TelegramBotsApi telegramBotsApi;
        try {
            telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(bot());
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
        return telegramBotsApi;
    }

    @Bean
    public Bot bot() {
        return new Bot(botToken, teseraService);
    }
}
