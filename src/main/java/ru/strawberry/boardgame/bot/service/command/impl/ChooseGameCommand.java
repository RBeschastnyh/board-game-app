package ru.strawberry.boardgame.bot.service.command.impl;

import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethodMessage;
import org.telegram.telegrambots.meta.api.methods.polls.SendPoll;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.strawberry.boardgame.bot.service.command.Command;
import ru.strawberry.boardgame.bot.service.command.CommandRequest;
import ru.strawberry.boardgame.repository.GamesRepository;
import ru.strawberry.boardgame.repository.TabletopRepository;
import ru.strawberry.boardgame.repository.dto.Games;
import ru.strawberry.boardgame.repository.dto.Tabletop;
import ru.strawberry.boardgame.repository.impl.GamesRepositoryImpl;
import ru.strawberry.boardgame.repository.impl.TabletopRepositoryImpl;

import java.util.List;
import java.util.Optional;

public class ChooseGameCommand implements Command {

    private final TabletopRepository tabletopRepository = new TabletopRepositoryImpl();
    private final GamesRepository gamesRepository = new GamesRepositoryImpl();

    @Override
    public BotApiMethodMessage process(CommandRequest command) {
        Long tgId = command.getTgId();

        Optional<Tabletop> tabletop = tabletopRepository.checkIfUserIsHost(tgId);
        if (tabletop.isPresent()) {
            List<String> games = gamesRepository.getGames(tgId)
                    .stream()
                    .map(Games::getTitle)
                    .toList();
            return SendPoll.builder()
                    .chatId(tgId)
                    .options(games)
                    .openPeriod(120)
                    .build();
        }

        return SendMessage.builder()
                .chatId(tgId)
                .text("У вас нет столов где Вы хост")
                .build();
    }
}
