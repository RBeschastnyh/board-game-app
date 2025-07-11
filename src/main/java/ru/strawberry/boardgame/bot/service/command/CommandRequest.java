package ru.strawberry.boardgame.bot.service.command;

import lombok.Builder;
import lombok.Getter;

/**
 * Object reflecting user command.
 *
 * @author RBeschastnykh
 */
@Builder
@Getter
public class CommandRequest {

    private Long tgId;
    private String command;
}
