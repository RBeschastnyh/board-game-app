package ru.strawberry.boardgame.bot.service.command.impl;

import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethodMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.strawberry.boardgame.bot.service.command.Command;
import ru.strawberry.boardgame.bot.service.command.CommandRequest;
import ru.strawberry.boardgame.repository.dto.Tabletop;
import ru.strawberry.boardgame.repository.TabletopRepository;
import ru.strawberry.boardgame.repository.impl.TabletopRepositoryImpl;

import java.util.Optional;

public class CloseTableCommand implements Command {
    private final TabletopRepository tabletopRepository = new TabletopRepositoryImpl();

    @Override
    public BotApiMethodMessage process(CommandRequest command) {
        String message = "У вас нет активных столов";
        Optional<Tabletop> tabletop = tabletopRepository.checkIfUserIsHost(command.getTgId());
        if (tabletop.isPresent()) {
            tabletopRepository.deleteTable(tabletop.get());
            message = String.format("Ваш стол %s закрыт!", tabletop.get().getCode());
        }

        return SendMessage.builder()
                .chatId(command.getTgId())
                .text(message)
                .build();
    }
}
