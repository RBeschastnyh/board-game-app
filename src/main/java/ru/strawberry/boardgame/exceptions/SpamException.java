package ru.strawberry.boardgame.exceptions;

/**
 * Exception for spam input.
 *
 * @author RBeschastnykh
 */
public class SpamException extends RuntimeException {

    public SpamException() {
        super();
    }

    public SpamException(String msg) {
        super(msg);
    }
}
