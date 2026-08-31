package ru.strawberry.boardgame.exceptions;

/**
 * Exception for database interactions.
 *
 * @author RBeschastnykh
 */
public class DatabaseException extends RuntimeException {

    public DatabaseException() {
        super();
    }

    public DatabaseException(String msg) {
        super(msg);
    }
}
