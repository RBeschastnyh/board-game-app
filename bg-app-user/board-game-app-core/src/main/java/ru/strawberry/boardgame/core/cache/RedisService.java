package ru.strawberry.boardgame.core.cache;

import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;

public class RedisService {

    private final RedisClient redisClient;

    public RedisService(RedisClient redisClient) {
        this.redisClient = redisClient;
    }

    public void setValue(String key, String value) {
        try (StatefulRedisConnection<String, String> session = redisClient.connect()) {
            RedisCommands<String, String> sync = session.sync();

            sync.set(key, value);
        }
    }
}
