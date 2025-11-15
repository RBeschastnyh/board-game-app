package ru.strawberry.boardgame.exceptions;

public class UserInputException extends IllegalArgumentException {

    public UserInputException() {
        super();
    }

    public UserInputException(String msg) {
        super(msg);
    }
}
