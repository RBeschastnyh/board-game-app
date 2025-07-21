package ru.strawberry.boardgame.bot;

import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.strawberry.boardgame.bot.service.CommandProcessor;
import ru.strawberry.boardgame.bot.service.command.CommandRequest;
import ru.strawberry.boardgame.bot.service.textprocessors.PlainTextProcessor;

/**
 * Bot class. Uses telegram api.
 *
 * @author RBeschastnykh
 */
@Slf4j
public class BoardGameBot extends TelegramLongPollingBot {

    private final CommandProcessor commandProcessor;
    private final PlainTextProcessor plainTextProcessor;

    public BoardGameBot(String token) {
        super(token);
        this.commandProcessor = new CommandProcessor();
        this.plainTextProcessor = new PlainTextProcessor();
    }

    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();

        log.info("Received message {}", message);

        String text = message.getText();
        String response = null;

        try {
            CommandRequest commandRequest = CommandRequest.builder()
                    .tgId(message.getChatId())
                    .command(text.startsWith("/") ? text.substring(1) : text)
                    .build();
            if (text.startsWith("/")) {
                response = commandProcessor.process(commandRequest);
            } else {
                response = plainTextProcessor.process(commandRequest);
            }

            SendMessage sendMessage = SendMessage.builder()
                    .chatId(message.getChatId())
                    .text(response)
                    .build();
            execute(sendMessage);
        } catch (TelegramApiException e) {
            log.error("Error while sending message!");
            throw new RuntimeException(e);
        } catch (IllegalArgumentException iaex) {
            log.error("Error while processing message!");
            SendMessage errorMessage = SendMessage.builder()
                    .chatId(message.getChatId())
                    .text(iaex.getMessage())
                    .build();

            try {
                execute(errorMessage);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
            throw new RuntimeException(iaex);
        }
    }

    @Override
    public String getBotUsername() {
        return "BoardGamesBot";
    }
}
