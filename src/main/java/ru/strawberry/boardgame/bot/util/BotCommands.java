package ru.strawberry.boardgame.bot.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Available user commands.
 *
 * @author RBeschastnykh
 */
@Getter
@RequiredArgsConstructor
public enum BotCommands {
    START("/start"),
    REGISTER_TESERA_USER("/regteserauser"),
    CHOOSE_GAME("/choosegame");

    private final String command;
}
