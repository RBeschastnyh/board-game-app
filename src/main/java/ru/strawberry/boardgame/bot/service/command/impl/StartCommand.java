package ru.strawberry.boardgame.bot.service.command.impl;

import lombok.extern.slf4j.Slf4j;
import ru.strawberry.boardgame.bot.service.command.Command;
import ru.strawberry.boardgame.bot.service.command.CommandRequest;
import ru.strawberry.boardgame.bot.service.redis.RedisService;
import ru.strawberry.boardgame.bot.service.user.UserService;

import java.util.ArrayList;
import java.util.List;

/**
 * Command for processing "/start" message.
 *
 * @author RBeschastnykh
 */
@Slf4j
public class StartCommand implements Command {

    private final UserService userService = UserService.getInstance();
    private final RedisService redisService = RedisService.getInstance();

    private final List<String> inappropriateStates = new ArrayList<>();

    private final String originalCommand;

    public StartCommand(String originalCommand) {
        this.originalCommand = originalCommand;
    }

    @Override
    public void process(CommandRequest command) {
        // Check if user exists
        this.validate(command.getTgId());
        userService.registerUser(command.getTgId());
        // create if not otherwise return
    }

    protected void validate(Long from) {
        log.info("Start processing start command");
        log.debug("Command text {}", this.originalCommand);
        if (this.originalCommand == null || this.originalCommand.isBlank()) {
            log.error("Incorrect input for start command! {}", this.originalCommand);
            throw new IllegalArgumentException("Ввод команды не может быть пустым!");
        }
        String[] tokens = this.originalCommand.split(" ");
        if (tokens.length != 1 || !tokens[0].equals("start")) {
            log.error("Incorrect input for start command! {}", this.originalCommand);
            throw new IllegalArgumentException("Неверный формат команды! Введите /start");
        }

        String state = redisService.checkIfExistsState(Long.toString(from));

        if (state != null && inappropriateStates.contains(state)) {
            log.error("Attempt to make inappropriate acton by {}! {}", from, this.originalCommand);
            throw new IllegalArgumentException("Недостуное действие!");
        }

        redisService.putState(Long.toString(from), "TO_REGISTER");
    }

    @Override
    public String getMessage() {
        return """
                Привет! Бот поможет выбрать настольную игра для компании.
                Как пользоваться?
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
    }
}
