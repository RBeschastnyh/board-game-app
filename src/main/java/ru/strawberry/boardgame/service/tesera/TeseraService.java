package ru.strawberry.boardgame.service.tesera;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import ru.strawberry.boardgame.bot.util.EnvVars;
import ru.strawberry.boardgame.exceptions.FetchingUserInfoException;
import ru.strawberry.boardgame.exceptions.TeseraServiceException;
import ru.strawberry.boardgame.exceptions.TeseraNotFoundException;
import ru.strawberry.boardgame.service.tesera.dto.ResponseTeseraGame;
import ru.strawberry.boardgame.service.tesera.dto.TeseraGame;
import ru.strawberry.boardgame.service.tesera.dto.TeseraUser;

import java.util.Collections;
import java.util.List;

/**
 * Tesera service.
 *
 * @author RBeschastnykh
 */
@Slf4j
public class TeseraService {
    private final ObjectMapper objectMapper = new ObjectMapper();

    public TeseraUser getUserByName(String username) {
        String teseraUrl = System.getenv(EnvVars.TESERA_URL.name());
        HttpGet httpGet = new HttpGet(teseraUrl + "/user/" + username);

        log.info("Ready to send request fot user with login {} to: {}", username, httpGet);

        try (CloseableHttpClient closeableHttpClient = HttpClients.createDefault()) {
            log.info("Sending request for {} to check if user exists on tesera", username);
            CloseableHttpResponse response = closeableHttpClient.execute(httpGet);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == HttpStatus.SC_NOT_FOUND) {
                log.info("User {} not found", username);
                throw new TeseraNotFoundException(username);
            } else if (statusCode != HttpStatus.SC_OK) {
                log.info("Non 200 response for {}", username);
                throw new TeseraServiceException();
            }

            String content = new String(response.getEntity().getContent().readAllBytes());
            log.trace("Creating answer");
            return objectMapper.readValue(content, TeseraUser.class);
        } catch (Exception e) {
            log.error("Error occurred during gathering user info! ", e);
            throw new FetchingUserInfoException("Повторите попытку позже, проблемы с доступом к Tesera");
        }
    }

    public List<TeseraGame> getListGames(@NonNull String username, int batchNum) {
        String teseraUrl = System.getenv(EnvVars.TESERA_URL.name());
        HttpGet httpGet = new HttpGet(teseraUrl + "/collections/base/own/" + username + "?GamesType=All&Offset=" + batchNum + "&Limit=30");

        log.info("Ready to send request for games for user with login {} to: {}", username, httpGet);

        try (CloseableHttpClient closableHttpClient = HttpClients.createDefault()) {
            CloseableHttpResponse response = closableHttpClient.execute(httpGet);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == HttpStatus.SC_NOT_FOUND) {
                log.info("No user {}", username);
                throw new TeseraNotFoundException(username);
            } else if (statusCode != HttpStatus.SC_OK) {
                log.info("Error occurred while sending games in collection request");
                throw new TeseraServiceException();
            }

            String content = new String(response.getEntity().getContent().readAllBytes());
            log.trace("Creating list of games for {}", username);
            List<ResponseTeseraGame> list = objectMapper.readValue(content, new TypeReference<>() {});
            log.info("Got list of games for user {}. List is: {}", username, list);
            if (!list.isEmpty()) {
                log.trace("Converting list of games for {}", username);
                return list.stream()
                        .map(ResponseTeseraGame::getGame)
                        .toList();
            }
            return Collections.emptyList();
        } catch (Exception e) {
            log.error("Error while retrieving games username: {}, batchNum: {}", username, batchNum, e);
            throw new RuntimeException(e);
        }
    }

}
