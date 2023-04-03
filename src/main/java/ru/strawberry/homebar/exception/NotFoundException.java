package ru.strawberry.homebar.exception;

/**
 * Exception, thrown when some entity is not found in DB.
 *
 * @author RBeschastnykh
 */
public class NotFoundException extends RuntimeException {

  private final ProblemEntity problemEntity;

  public NotFoundException(ProblemEntity problemEntity) {
    super();
    this.problemEntity = problemEntity;
  }

  public NotFoundException(ProblemEntity problemEntity, String message) {
    super(message);
    this.problemEntity = problemEntity;
  }

  public NotFoundException(ProblemEntity problemEntity,String message, Throwable cause) {
    super(message, cause);
    this.problemEntity = problemEntity;
  }

  public ProblemEntity getProblemEntity() {
    return this.problemEntity;
  }
}
