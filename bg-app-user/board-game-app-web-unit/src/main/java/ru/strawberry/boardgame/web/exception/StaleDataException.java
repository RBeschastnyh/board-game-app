package ru.strawberry.boardgame.web.exception;

import lombok.Getter;

public class StaleDataException extends RuntimeException {

    @Getter
    private final ExceptionAppCodes code;

    public StaleDataException(ExceptionAppCodes code, String message) {
        super(message);
        this.code = code;
    }
}
