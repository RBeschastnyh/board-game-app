package ru.strawberry.boardgame.bot.service.redis;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RedisService {

    private static RedisService redisService;
    private RedisClient redisClient;

    public static synchronized RedisService getInstance() {
        if (redisService == null) {
            redisService = new RedisService(
                    RedisClient.create(
                            RedisURI.create(System.getenv("REDIS_DB"), 6379)
                    )
            );
        }

        return redisService;
    }

    private RedisService(RedisClient redisClient) {
        this.redisClient = redisClient;
    }


    public String checkIfExistsState(String key) {
        try (StatefulRedisConnection<String, String> conn = this.redisClient.connect()) {
            RedisCommands<String, String> synced = conn.sync();
            return synced.get(key + "-STATE");
        }
    }

    public void putState(String key, String state) {
        try (StatefulRedisConnection<String, String> conn = this.redisClient.connect()) {
            RedisCommands<String, String> synced = conn.sync();
            synced.set(key + "-STATE", state);
        }
    }
}
