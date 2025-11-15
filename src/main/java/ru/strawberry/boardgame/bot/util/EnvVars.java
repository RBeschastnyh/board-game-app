package ru.strawberry.boardgame.bot.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * System vars.
 *
 * @author RBeschastnykh
 */
@Getter
@RequiredArgsConstructor
public enum EnvVars {
    FLYWAY_URL,
    FLYWAY_USER,
    FLYWAY_PASSWORD,
    FLYWAY_SCHEMA,
    BOT_TOKEN,
    REDIS_DB,
    TESERA_URL
}
