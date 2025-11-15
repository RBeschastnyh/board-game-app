package ru.strawberry.boardgame.repository.redis;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import lombok.extern.slf4j.Slf4j;
import ru.strawberry.boardgame.bot.util.EnvVars;

/**
 * Redis service.
 *
 * @author RBeschastnykh
 */
@Slf4j
public class RedisService {

    private static RedisService redisService;
    private RedisClient redisClient;

    public static synchronized RedisService getInstance() {
        if (redisService == null) {
            redisService = new RedisService(
                    RedisClient.create(
                            RedisURI.create(System.getenv(EnvVars.REDIS_DB.name()), 9005)
                    )
            );
        }

        return redisService;
    }

    private RedisService(RedisClient redisClient) {
        this.redisClient = redisClient;
    }


    public RedisUserState checkIfExistsState(String key) {
        log.trace("Got request for checking value for key {}", key);
        try (StatefulRedisConnection<String, String> conn = this.redisClient.connect()) {
            RedisCommands<String, String> synced = conn.sync();
            log.trace("Connection for read established for key {}", key);
            String state = synced.get(key);
            log.info("Got value {} by key {}", state, key);
            if (state != null) {
                return RedisUserState.valueOf(state);
            }
            return null;
        }
    }

    public void putState(String key, RedisUserState state) {
        log.trace("Putting value {} for key {}", state, key);
        try (StatefulRedisConnection<String, String> conn = this.redisClient.connect()) {
            RedisCommands<String, String> synced = conn.sync();
            log.trace("Connection for write established for key {}", key);
            synced.set(key, state.name());
        }
    }
}
