package ru.strawberry.boardgame.web.exception;

import lombok.Getter;

public class DataNotFoundException extends RuntimeException {

    @Getter
    private final ExceptionAppCodes code;

    public DataNotFoundException(ExceptionAppCodes code, String message) {
        super(message);

        this.code = code;
    }
}
