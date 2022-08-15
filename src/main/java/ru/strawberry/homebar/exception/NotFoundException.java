package ru.strawberry.homebar.exception;

/**
 * Exception, thrown when some entity is not found in DB.
 *
 * @author RBeschastnykh
 */
public class NotFoundException extends Exception {

  public NotFoundException() {
    super();
  }

  public NotFoundException(String message) {
    super(message);
  }

  public NotFoundException(String message, Throwable cause) {
    super(message, cause);
  }
}
