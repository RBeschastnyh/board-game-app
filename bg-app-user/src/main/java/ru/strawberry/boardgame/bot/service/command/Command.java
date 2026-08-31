package ru.strawberry.boardgame.bot.service.command;

import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethodMessage;

/**
 * User input command.
 *
 * @author RBeschastnykh
 */
public interface Command {

    /**
     * Process command. This is asynchronous operation, user will get response after all actions
     */
    BotApiMethodMessage process(CommandRequest command);

}
