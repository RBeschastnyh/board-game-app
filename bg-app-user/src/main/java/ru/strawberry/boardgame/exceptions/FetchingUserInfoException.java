package ru.strawberry.boardgame.exceptions;

/**
 * Tesera user checking error.
 *
 * @author RBeschastnykh
 */
public class FetchingUserInfoException extends RuntimeException {

    public FetchingUserInfoException() {
        super();
    }

    public FetchingUserInfoException(String msg) {
        super(msg);
    }
}
