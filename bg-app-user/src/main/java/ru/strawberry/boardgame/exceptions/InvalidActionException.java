package ru.strawberry.boardgame.exceptions;

/**
 * Exception for mismatch in user commands.
 *
 * @author RBeschastnykh
 */
public class InvalidActionException extends RuntimeException {

    public InvalidActionException() {
        super();
    }

    public InvalidActionException(String msg) {
        super(msg);
    }

}
