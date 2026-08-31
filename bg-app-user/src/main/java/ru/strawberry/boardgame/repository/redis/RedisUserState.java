package ru.strawberry.boardgame.repository.redis;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RedisUserState {
    TO_REGISTER,
    REGISTRATION_IN_PROCESS,
    REGISTRATION_COMPLETE,
    FETCHING_GAMES,
    AWAITING_VERIFICATION,
    ACCOUNT_VERIFIED,
    JOINING_TABLE,
    JOINED_TABLE,
    READY_TO_CHOOSE_GAME,
    CHOOSING_GAME
}
