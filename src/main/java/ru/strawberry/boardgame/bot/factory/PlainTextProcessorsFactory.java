package ru.strawberry.boardgame.bot.factory;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import ru.strawberry.boardgame.bot.service.textprocessors.impl.JoinTableTextProcessor;
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
        if (RedisUserState.REGISTRATION_IN_PROCESS == state) {
            log.info("Start processing tesera registration");
            return new RegTeseraUserTextProcessor();
        }
        if (RedisUserState.JOINING_TABLE == state) {
            log.info("Start processing joining table");
            return new JoinTableTextProcessor();
        }

        throw new SpamException("");
    }
}
