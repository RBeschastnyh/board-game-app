package ru.strawberry.boardgame.bot.service.command.impl;

import lombok.extern.slf4j.Slf4j;
import ru.strawberry.boardgame.bot.service.command.Command;

/**
 * Command for processing "/start" message.
 *
 * @author RBeschastnykh
 */
@Slf4j
public class StartCommand implements Command {

    private final String originalCommand;

    public StartCommand(String originalCommand) {
        this.originalCommand = originalCommand;
    }

    @Override
    public void process() {

    }

    @Override
    public void validate() {
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
    }

    @Override
    public String getMessage() {
        return "Привет! Бот поможет выбрать настольную игра для компании." +
                "\nКак пользоваться?" +
                "\nВ идеале, каждому (или почти) в компании потребуется телефон с Telegram." +
                "\n1) Владелец коллекции (далее хост) генерирует код стола командой /create" +
                "\n2) Его товарищи присоединяются к столу введя команду /join и введя код стола следующим сообщением " +
                "(бот попросит)" +
                "\n3) После того как присоединились все желающие хост командой /choose генерирует голосовалку из " +
                "некоторого количества настолок." +
                "Почему некоторого? Потому что бота можно настраивать под компанию! Но об этом по позже" +
                "\n4) После того, как все проголосовали возможны два варианта:" +
                "\n4.1) Была выбрана хотя бы одна игра. Ура!" +
                "\n4.2) Игры не подошли((9( Сначала бот спросит по поводу каждой игры почему она не подошла " +
                "(просто протыкать, ничего писать не нужно), затем буду предложены новые игры" +
                "\n5) Вы можете оценить игру, это поможет в рекомендациях. Для этого используйте команду /rate и " +
                "выберете игру, которую хотите оценить. Оценки - эмодзики, выберете тот, что ближе всего по ощущениям" +
                "\n\nСтол считается активным на протяжении 12 часов. Стол можно закрыть принудительно командой /close" +
                "\n\n";
    }
}
