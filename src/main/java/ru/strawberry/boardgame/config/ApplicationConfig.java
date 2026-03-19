package ru.strawberry.boardgame.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;

@Configuration
@PropertySource("classpath:application-local.properties")
@ComponentScan(
        basePackages = {
                "ru.strawberry.boardgame"
        }
)
public class ApplicationConfig {

    @Autowired
    private Environment environment;

    @Bean
    public RedisClient redisClient() {
        return RedisClient.create(
                RedisURI.create(environment.getProperty("redis.url"),
                        Integer.valueOf(environment.getProperty("redis.port"))
                )
        );
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

}
