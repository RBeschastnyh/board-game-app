package ru.strawberry.tesera.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.strawberry.tesera.config.params.TeseraConfigParams;
import ru.strawberry.tesera.dto.AuthRequest;
import ru.strawberry.tesera.dto.AuthResponse;
import ru.strawberry.tesera.service.TeseraService;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class TeseraServiceImpl implements TeseraService {
    private final TeseraConfigParams teseraConfigParams;
    private final ObjectMapper objectMapper;

    public TeseraServiceImpl(TeseraConfigParams teseraConfigParams, ObjectMapper objectMapper) {
        this.teseraConfigParams = teseraConfigParams;
        this.objectMapper = objectMapper;
    }

    @Override
    public List<String> getUserGames() {
        return null;
    }

    @Override
    public AuthResponse auth() {
        try {
            String authRequest = objectMapper.writeValueAsString(new AuthRequest(teseraConfigParams.getUsername(), teseraConfigParams.getPassword()));
            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .uri(new URI(teseraConfigParams.getUrl().concat("/auth/login")))
                    .timeout(Duration.of(10, ChronoUnit.SECONDS))
                    .POST(HttpRequest.BodyPublishers.ofByteArray(authRequest.getBytes()))
                    .header("Content-Type", "application/json")
                    .build();

            HttpResponse<String> response = HttpClient.newBuilder()
                    .build()
                    .send(httpRequest, HttpResponse.BodyHandlers.ofString());

            int a = 0;
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        return null;
    }
}
