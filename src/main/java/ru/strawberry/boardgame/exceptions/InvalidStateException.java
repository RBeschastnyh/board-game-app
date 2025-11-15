package ru.strawberry.boardgame.exceptions;


public class InvalidStateException extends RuntimeException {

    public InvalidStateException(String msg) {
        super(msg);
    }
}
