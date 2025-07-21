package ru.strawberry.boardgame.bot.service.tesera;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import ru.strawberry.boardgame.bot.service.tesera.dto.TeseraUser;

@Slf4j
public class TeseraService {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public void gteUserByName(String username) {
        String teseraUrl = System.getenv("TESERA_URL");

        HttpGet httpGet = new HttpGet(teseraUrl + "/user/" + username);

        try (CloseableHttpClient closeableHttpClient = HttpClients.createDefault()) {
            CloseableHttpResponse response = closeableHttpClient.execute(httpGet);
            if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                throw new Exception("Для обработки ошибки кароче");
            }

            String content = new String(response.getEntity().getContent().readAllBytes());
            TeseraUser user = objectMapper.readValue(content, TeseraUser.class);
            int a = 0;

        } catch (Exception e) {
            log.error("Error occurred during gathering user info! ", e);
            throw new RuntimeException("Повторите попытку позже, проблемы с доступом к Tesera");
        }



    }

}
