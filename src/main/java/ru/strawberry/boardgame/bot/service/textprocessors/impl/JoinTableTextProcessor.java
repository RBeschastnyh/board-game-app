package ru.strawberry.boardgame.bot.service.textprocessors.impl;

import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethodMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.strawberry.boardgame.bot.service.command.CommandRequest;
import ru.strawberry.boardgame.bot.service.textprocessors.TextProcessor;
import ru.strawberry.boardgame.repository.dto.Tabletop;
import ru.strawberry.boardgame.repository.dto.User;
import ru.strawberry.boardgame.repository.PartyRepository;
import ru.strawberry.boardgame.repository.impl.PartyRepositoryImpl;
import ru.strawberry.boardgame.repository.redis.RedisService;
import ru.strawberry.boardgame.repository.redis.RedisUserState;
import ru.strawberry.boardgame.repository.TabletopRepository;
import ru.strawberry.boardgame.repository.impl.TabletopRepositoryImpl;
import ru.strawberry.boardgame.repository.UserRepository;
import ru.strawberry.boardgame.repository.impl.UserRepositoryImpl;

import java.util.Optional;

public class JoinTableTextProcessor implements TextProcessor {

    private final RedisService redisService = RedisService.getInstance();
    private final TabletopRepository tabletopRepository = new TabletopRepositoryImpl();
    private final PartyRepository partyRepository = new PartyRepositoryImpl();
    private final UserRepository userRepository = new UserRepositoryImpl();

    @Override
    public BotApiMethodMessage process(CommandRequest request) {
        String message = "Стола с таким кодом не существует!";
        Optional<Tabletop> tabletop = tabletopRepository.checkIfTableExistsByCode(request.getCommand());
        if (tabletop.isPresent()) {
            if (tabletop.get().getIsShut()) {
                message = String.format("Стол %s уже закрыт..", tabletop.get().getCode());
            } else {
                partyRepository.addUserToTable(request.getTgId(), tabletop.get().getId());
                redisService.putState(request.getTgId() + "-STATE", RedisUserState.JOINED_TABLE);
                message = String.format("Вы присоединились к столу %s", tabletop.get().getCode());
            }
        }

        // TODO: добавить кэширование для УМЕНЬШЕНИЯ НАГРУЗКИ на базейку
        Optional<User> user = userRepository.getByTgId(request.getTgId());
        if (user.isPresent()) {
            redisService.putState(request.getTgId() + "-STATE", RedisUserState.REGISTRATION_COMPLETE);
        } else {
            redisService.putState(request.getTgId() + "-STATE", RedisUserState.TO_REGISTER);
        }

        return SendMessage.builder()
                .chatId(request.getTgId())
                .text(message)
                .build();
    }
}
