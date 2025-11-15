package ru.strawberry.boardgame.exceptions;

/**
 * Common tesera interactions error.
 *
 * @author RBeschastnykh
 */
public class TeseraServiceException extends Exception {

    public TeseraServiceException() {
        super("Error while processing request to tesera");
    }

    public TeseraServiceException(String msg) {
        super(msg);
    }

    public TeseraServiceException(String msg, Throwable throwable) {
        super(msg, throwable);
    }
}
