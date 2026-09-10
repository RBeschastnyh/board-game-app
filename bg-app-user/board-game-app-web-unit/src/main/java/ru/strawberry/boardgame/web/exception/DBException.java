package ru.strawberry.boardgame.web.exception;

public class DBException extends RuntimeException {

    public DBException(String message) {
        super(message);
    }
}
