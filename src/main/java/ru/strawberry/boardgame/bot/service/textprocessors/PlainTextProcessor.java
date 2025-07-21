package ru.strawberry.boardgame.bot.service.textprocessors;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.strawberry.boardgame.bot.service.command.CommandRequest;
import ru.strawberry.boardgame.bot.service.redis.RedisService;
import ru.strawberry.boardgame.bot.service.textprocessors.factory.PlainTextProcessorsFactory;

@Slf4j
public class PlainTextProcessor {

    private final PlainTextProcessorsFactory plainTextProcessorsFactory = new PlainTextProcessorsFactory();

    public String process(CommandRequest commandRequest) {
        RedisService redisService = RedisService.getInstance();

        String state = redisService.checkIfExistsState(commandRequest.getTgId().toString());

        if (state == null) {
            log.error("Spam message by user {}", commandRequest.getTgId());
            throw new RuntimeException("Спам");
        }

        TextProcessor textProcessor = plainTextProcessorsFactory.create(state);
        textProcessor.process(commandRequest.getCommand());
        return "cgfcb,!";
    }
}
