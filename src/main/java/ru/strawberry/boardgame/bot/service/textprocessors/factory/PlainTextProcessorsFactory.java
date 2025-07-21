package ru.strawberry.boardgame.bot.service.textprocessors.factory;

import lombok.NonNull;
import ru.strawberry.boardgame.bot.service.redis.RedisService;
import ru.strawberry.boardgame.bot.service.textprocessors.TextProcessor;
import ru.strawberry.boardgame.bot.service.textprocessors.impl.RegTeseraUserTextProcessor;

public class PlainTextProcessorsFactory {

    public TextProcessor create(@NonNull String state) {
        if ("REGISTRATION_IN_PROCESS".equals(state)) {
            return new RegTeseraUserTextProcessor();
        }

        return null;
    }
}
