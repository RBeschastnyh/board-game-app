package ru.strawberry.tesera.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import ru.strawberry.tesera.config.params.TeseraConfigParams;
import ru.strawberry.tesera.service.TeseraService;
import ru.strawberry.tesera.service.impl.TeseraServiceImpl;

@Configuration
@PropertySource("classpath:application.properties")
public class BoardGameUnitConfiguration {

    @Value("${tesera.url}") private String url;
    @Value("${tesera.username}") private String username;
    @Value("${tesera.password}") private String password;

    @Bean
    public TeseraConfigParams teseraConfigParams() {
        return new TeseraConfigParams(url, username, password);
    }

    @Bean
    public TeseraService teseraService() {
        return new TeseraServiceImpl(teseraConfigParams(), objectMapper());
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}
