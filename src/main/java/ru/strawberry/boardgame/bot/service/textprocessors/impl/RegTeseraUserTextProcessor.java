package ru.strawberry.boardgame.bot.service.textprocessors.impl;

import ru.strawberry.boardgame.bot.service.tesera.TeseraService;
import ru.strawberry.boardgame.bot.service.textprocessors.TextProcessor;

public class RegTeseraUserTextProcessor implements TextProcessor {

    private final TeseraService teseraService = new TeseraService();

    @Override
    public String process(String text) {
        teseraService.gteUserByName(text);
        return "";
    }
}
