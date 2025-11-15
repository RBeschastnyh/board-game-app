package ru.strawberry.boardgame.bot.service.textprocessors;

import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethodMessage;
import ru.strawberry.boardgame.bot.service.command.CommandRequest;

/**
 * User-input text processor. Processes text for command specified by realization.
 *
 * @author RBeschastnykh
 */
public interface TextProcessor {

    BotApiMethodMessage process(CommandRequest request);
}
