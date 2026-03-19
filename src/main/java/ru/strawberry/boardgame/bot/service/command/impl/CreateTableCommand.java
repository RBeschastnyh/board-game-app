package ru.strawberry.boardgame.bot.service.command.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethodMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.strawberry.boardgame.bot.service.command.Command;
import ru.strawberry.boardgame.bot.service.command.CommandRequest;
import ru.strawberry.boardgame.repository.redis.RedisService;
import ru.strawberry.boardgame.repository.redis.RedisUserState;
import ru.strawberry.boardgame.repository.TabletopRepository;

import java.util.List;
import java.util.Random;

@Slf4j
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class CreateTableCommand implements Command {

    private static final List<RedisUserState> goodStates = List.of(
            RedisUserState.REGISTRATION_COMPLETE,
            RedisUserState.READY_TO_CHOOSE_GAME
    );

    private final RedisService redisService;
    private final TabletopRepository tabletopRepository;

    @Autowired
    public CreateTableCommand(RedisService redisService, TabletopRepository tabletopRepository) {
        this.redisService = redisService;
        this.tabletopRepository = tabletopRepository;
    }

    @Override
    public BotApiMethodMessage process(CommandRequest command) {
        String message = "Вы не можете создать стол";
        Long tgId = command.getTgId();
        RedisUserState state = redisService.checkIfExistsState(tgId + "-STATE");

        if (state != null && goodStates.contains(state)) {
            if (tabletopRepository.checkIfUserIsHost(tgId).isPresent()) {
                message = """
                        У вас уже есть активный стол.
                        Если хотите создать новый, сначала закройте текущий. Используйте команду /close
                        """;
            } else {
                Random random = new Random(System.currentTimeMillis());
                while (true) {
                    StringBuilder stringBuilder = new StringBuilder();
                    for (int i = 0; i < 6; i++) {
                        int nextInt = random.nextInt(36);
                        if (nextInt <= 9) {
                            stringBuilder.append(nextInt);
                        } else {
                            stringBuilder.append((char) (nextInt + 55));
                        }
                    }

                    String code = stringBuilder.toString();
                    if (tabletopRepository.checkIfTableExistsByCode(code).isEmpty()) {
                        message = code;
                        tabletopRepository.addTable(tgId, code);
                        break;
                    }
                }
            }
        }

        return SendMessage.builder()
                .chatId(tgId)
                .text(message)
                .build();
    }
}
