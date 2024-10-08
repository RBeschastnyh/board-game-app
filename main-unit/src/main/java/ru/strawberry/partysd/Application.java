package ru.strawberry.partysd;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import ru.strawberry.partysd.config.ApplicationConfiguration;

public class Application {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(ApplicationConfiguration.class);
        applicationContext.getBean(TelegramBotsApi.class);
    }
}