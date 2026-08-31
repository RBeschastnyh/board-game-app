package ru.strawberry.boardgame.bot.service.command.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethodMessage;
import org.telegram.telegrambots.meta.api.methods.polls.SendPoll;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.strawberry.boardgame.bot.service.command.Command;
import ru.strawberry.boardgame.bot.service.command.CommandRequest;
import ru.strawberry.boardgame.repository.GamesRepository;
import ru.strawberry.boardgame.repository.TabletopRepository;
import ru.strawberry.boardgame.repository.dto.Tabletop;

import java.util.List;
import java.util.Optional;

@Slf4j
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ChooseGameCommand implements Command {

    private final TabletopRepository tabletopRepository;
    private final GamesRepository gamesRepository;

    @Autowired
    public ChooseGameCommand(TabletopRepository tabletopRepository, GamesRepository gamesRepository) {
        this.gamesRepository = gamesRepository;
        this.tabletopRepository = tabletopRepository;
    }

    @Override
    public BotApiMethodMessage process(CommandRequest command) {
        Long tgId = command.getTgId();

        Optional<Tabletop> tabletop = tabletopRepository.checkIfUserIsHost(tgId);
        if (tabletop.isPresent()) {
            List<String> games = gamesRepository.getGamesTitles(tgId);

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
