package ru.strawberry.homebar.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.strawberry.homebar.dto.error.ErrorInfoDto;

/**
 * Transforms exceptions to error DTO.
 *
 * @author RBeschastnykh
 */
@RestControllerAdvice
public class RestExceptionHandler {

  @ExceptionHandler({NotFoundException.class})
  public ResponseEntity<ErrorInfoDto> handleNotFoundException(NotFoundException exception) {
    ErrorInfoDto errorInfoDto = new ErrorInfoDto();
    errorInfoDto.setMessage(exception.getMessage());
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorInfoDto);
  }

  @ExceptionHandler({GuestAlreadyExistsException.class})
  public ResponseEntity<ErrorInfoDto> handleGuestAlreadyExistsException(GuestAlreadyExistsException exception) {
    ErrorInfoDto errorInfoDto = new ErrorInfoDto();
    errorInfoDto.setMessage(exception.getMessage());
    return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(errorInfoDto);
  }

  @ExceptionHandler({Exception.class})
  public ResponseEntity<ErrorInfoDto> handleNotFoundException(Exception exception) {
    ErrorInfoDto errorInfoDto = new ErrorInfoDto();
    errorInfoDto.setMessage(exception.getMessage());
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorInfoDto);
  }
}
