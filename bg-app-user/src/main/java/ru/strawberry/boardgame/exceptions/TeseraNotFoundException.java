package ru.strawberry.boardgame.exceptions;

import lombok.NonNull;

public class TeseraNotFoundException extends Exception {

    public TeseraNotFoundException(@NonNull String userCause) {
        super("Not found user with name " + userCause);
    }

    public TeseraNotFoundException(@NonNull String userCause, Throwable throwable) {
        super("Not found user with name " + userCause, throwable);
    }
}
