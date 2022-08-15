package ru.strawberry.homebar.exception;

/**
 * Exception thrown when user attempts to create account with already existing login.
 *
 * @author RBeschastnykh
 */
public class GuestAlreadyExistsException extends Exception {

  public GuestAlreadyExistsException() {
    super();
  }

  public GuestAlreadyExistsException(String message) {
    super(message);
  }

  public GuestAlreadyExistsException(String message, Throwable cause) {
    super(message, cause);
  }
}
