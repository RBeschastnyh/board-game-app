package ru.strawberry.partysd.bot;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.strawberry.tesera.service.TeseraService;

import java.nio.charset.StandardCharsets;

/**
 * Main bot for all purposes.
 *
 * @author RBeschastnykh
 */
@Component
public class Bot extends TelegramLongPollingBot {

    private final TeseraService teseraService;

    public Bot(String token, TeseraService teseraService) {
        super(token);
        this.teseraService = teseraService;
    }

    @Override
    public void onUpdateReceived(Update update) {
        var message = update.getMessage();
        if (isCommand(message)) {
            teseraService.auth();
            SendMessage sendMessage = SendMessage.builder()
                    .chatId(message.getChatId())
                    .text(new String("Ответ".getBytes(), StandardCharsets.UTF_8))
                    .build();
            try {
                execute(sendMessage);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        }
        var msg = update.getMessage();
        var user = msg.getFrom();
    }

    private boolean isCommand(Message message) {
        return message != null && message.getText().startsWith("/");
    }

    @Override
    public String getBotUsername() {
        return "StrawberryServiceDesk";
    }
}

