package ru.strawberry.boardgame.bot;

import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethodMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.strawberry.boardgame.exceptions.UserInputException;
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
        BotApiMethodMessage response;

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

            execute(response);
        } catch (TelegramApiException e) {
            log.error("Error while sending message!");
            throw new RuntimeException(e);
        } catch (UserInputException iaex) {
            log.error("Error while processing message!");
            SendMessage errorMessage = SendMessage.builder()
                    .chatId(message.getChatId())
                    .text(iaex.getMessage())
                    .build();

            try {
                execute(errorMessage);
            } catch (TelegramApiException e) {
                log.error("Error sending error message to user. Telegram issue", e);
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
