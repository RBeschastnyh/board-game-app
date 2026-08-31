package ru.strawberry.boardgame.bot.service.textprocessors.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethodMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.strawberry.boardgame.bot.service.command.CommandRequest;
import ru.strawberry.boardgame.bot.service.textprocessors.TextProcessor;
import ru.strawberry.boardgame.repository.dto.Tabletop;
import ru.strawberry.boardgame.repository.dto.User;
import ru.strawberry.boardgame.repository.PartyRepository;
import ru.strawberry.boardgame.repository.redis.RedisService;
import ru.strawberry.boardgame.repository.redis.RedisUserState;
import ru.strawberry.boardgame.repository.TabletopRepository;
import ru.strawberry.boardgame.repository.UserRepository;

import java.util.Optional;

@Slf4j
@Component
public class JoinTableTextProcessor implements TextProcessor {

    private final RedisService redisService;
    private final TabletopRepository tabletopRepository;
    private final PartyRepository partyRepository;
    private final UserRepository userRepository;

    @Autowired
    public JoinTableTextProcessor(RedisService redisService, TabletopRepository tabletopRepository, PartyRepository partyRepository, UserRepository userRepository) {
        this.redisService = redisService;
        this.tabletopRepository = tabletopRepository;
        this.partyRepository = partyRepository;
        this.userRepository = userRepository;
    }

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
