package ru.strawberry.boardgame.bot.service.command;

/**
 * User input command.
 *
 * @author RBeschastnykh
 */
public interface Command {

    /**
     * Process command. This is asynchronous operation, user will get response after all actions
     */
    void process(CommandRequest command);

    /**
     * Message to return to user. Relies on current state, so message is intermediate.
     *
     * @return text to display to user
     */
    String getMessage();
}
