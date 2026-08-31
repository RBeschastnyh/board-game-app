package ru.strawberry.boardgame.bot.service.textprocessors;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethodMessage;
import ru.strawberry.boardgame.bot.service.command.CommandRequest;
import ru.strawberry.boardgame.repository.redis.RedisService;
import ru.strawberry.boardgame.bot.factory.PlainTextProcessorsFactory;
import ru.strawberry.boardgame.repository.redis.RedisUserState;

/**
 * Top-level processor for user-input. <p>
 * Here {@link PlainTextProcessorsFactory} decides how this text should be processed.
 *
 * @author RBeschastnykh
 */
@Slf4j
@Service
public class PlainTextProcessor {
    private final PlainTextProcessorsFactory plainTextProcessorsFactory;
    private final RedisService redisService;

    @Autowired
    public PlainTextProcessor(PlainTextProcessorsFactory plainTextProcessorsFactory, RedisService redisService) {
        this.plainTextProcessorsFactory = plainTextProcessorsFactory;
        this.redisService = redisService;
    }

    public BotApiMethodMessage process(CommandRequest commandRequest) {
        RedisUserState state = redisService.checkIfExistsState(commandRequest.getTgId() + "-STATE");

        if (state == null) {
            log.error("Spam message by user {}", commandRequest.getTgId());
            throw new RuntimeException("Спам");
        }

        return plainTextProcessorsFactory.create(state)
                .process(commandRequest);
    }
}
