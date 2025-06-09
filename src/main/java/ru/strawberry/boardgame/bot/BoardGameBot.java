package ru.strawberry.boardgame.bot;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

public class BoardGameBot extends TelegramLongPollingBot {

    public BoardGameBot(String token) {
        super(token);
    }

    @Override
    public void onUpdateReceived(Update update) {
    }

    @Override
    public String getBotUsername() {
        return "BoardGamesBot";
    }
}
