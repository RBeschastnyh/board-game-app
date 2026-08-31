package ru.strawberry.boardgame.bot.service.command.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethodMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.strawberry.boardgame.exceptions.UserInputException;
import ru.strawberry.boardgame.bot.service.command.Command;
import ru.strawberry.boardgame.bot.service.command.CommandRequest;
import ru.strawberry.boardgame.repository.redis.RedisService;
import ru.strawberry.boardgame.repository.redis.RedisUserState;
import ru.strawberry.boardgame.service.UserService;

/**
 * Command for processing "/start" message.
 *
 * @author RBeschastnykh
 */
@Slf4j
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class StartCommand implements Command {

    private final UserService userService;
    private final RedisService redisService;

    @Autowired
    public StartCommand(UserService userService, RedisService redisService) {
        this.userService = userService;
        this.redisService = redisService;
    }

    @Override
    public BotApiMethodMessage process(CommandRequest command) {
        // Check if user exists
        this.validate(command.getTgId());
        userService.registerUser(command.getTgId());
        // create if not otherwise return

        String responseText = """
                Привет! Бот поможет выбрать настолку
                В идеале, каждому (или почти) в компании потребуется телефон с Telegram.
                Очень рекомендуется зарегистрировать с помощью команды /regteserauser свой аккаунт на Тесере (https://tesera.ru/)
                1) Владелец коллекции (далее хост) генерирует код стола командой /create
                2) Его товарищи присоединяются к столу введя команду /join и введя код стола следующим сообщением (бот попросит)
                3) После того как присоединились все желающие хост командой /choose генерирует голосовалку из некоторого количества настолок.Почему некоторого? Потому что бота можно настраивать под компанию! Но об этом по позже
                4) После того, как все проголосовали возможны два варианта:
                4.1) Была выбрана хотя бы одна игра. Ура!
                4.2) Игры не подошли((9( Сначала бот спросит по поводу каждой игры почему она не подошла (просто протыкать, ничего писать не нужно), затем буду предложены новые игры
                5) Вы можете оценить игру, это поможет в рекомендациях. Для этого используйте команду /rate и выберете игру, которую хотите оценить. Оценки - эмодзики, выберете тот, что ближе всего по ощущениям

                Стол считается активным на протяжении 12 часов. Стол можно закрыть принудительно командой /close
                """;

        return SendMessage.builder()
                .chatId(command.getTgId())
                .text(responseText)
                .build();
    }

    private void validate(Long from) {
        log.info("Start processing start command");

        RedisUserState state = redisService.checkIfExistsState(from + "-STATE");

        if (state != null) {
            throw new UserInputException("Недостуное действие!");
        }

        redisService.putState(from + "-STATE" , RedisUserState.TO_REGISTER);
    }
}
