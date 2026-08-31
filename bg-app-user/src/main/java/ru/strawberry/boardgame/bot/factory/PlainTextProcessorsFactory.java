package ru.strawberry.boardgame.bot.factory;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;
import ru.strawberry.boardgame.bot.service.textprocessors.impl.JoinTableTextProcessor;
import ru.strawberry.boardgame.config.ApplicationConfig;
import ru.strawberry.boardgame.exceptions.SpamException;
import ru.strawberry.boardgame.repository.redis.RedisUserState;
import ru.strawberry.boardgame.bot.service.textprocessors.TextProcessor;
import ru.strawberry.boardgame.bot.service.textprocessors.impl.RegTeseraUserTextProcessor;

/**
 * Factory for classes responsible for plain (non-command) user input.
 * <p>
 * Typically, adding new command requests a paired text processor and state.
 * </p>
 *
 * @author RBeschastnykh
 */
@Slf4j
@Component
public class PlainTextProcessorsFactory {

    /**
     * Gets a text processor depending on the state.
     * <p>
     * User-input validation is implemented in processors-classes.
     * </p>
     *
     * @param state current user flow state. It is usually updated from command processors.
     * @return {@code TextProcessor} instance for current state.
     */
    public TextProcessor create(@NonNull RedisUserState state) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(ApplicationConfig.class);

        return switch (state) {
            case REGISTRATION_IN_PROCESS -> {
                log.info("Start processing tesera registration");
                yield applicationContext.getBean(RegTeseraUserTextProcessor.class);
            }
            case JOINING_TABLE -> {
                log.info("Start processing joining table");
                yield applicationContext.getBean(JoinTableTextProcessor.class);
            }
            default -> throw new SpamException("");
        };
    }
}
