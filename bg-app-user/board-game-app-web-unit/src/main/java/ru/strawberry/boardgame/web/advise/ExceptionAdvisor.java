package ru.strawberry.boardgame.web.advise;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.strawberry.boardgame.web.exception.DBException;
import ru.strawberry.boardgame.web.exception.DataNotFoundException;
import ru.strawberry.boardgame.web.exception.DuplicateEntityException;
import ru.strawberry.boardgame.web.exception.StaleDataException;

import java.io.Serial;
import java.io.Serializable;

@RestControllerAdvice
public class ExceptionAdvisor {

    @ExceptionHandler(exception = DBException.class)
    public ResponseEntity<Serializable> handleDbException(DBException ex) {
        return ResponseEntity
                .status(400)
                .body(ex.getMessage());
    }

    @ExceptionHandler(exception = DataNotFoundException.class)
    public ResponseEntity<Serializable> handleDataNotFoundException(DataNotFoundException e) {
        return ResponseEntity
                .status(404)
                .body(new Response(e.getCode().name(), e.getMessage()));
    }

    @ExceptionHandler(exception = DuplicateEntityException.class)
    public ResponseEntity<Serializable> handleDuplicateEntityException(DuplicateEntityException e) {
        return ResponseEntity
                .status(409)
                .body(e.getMessage());
    }

    @ExceptionHandler(exception = IllegalArgumentException.class)
    public ResponseEntity<Serializable> handleIllegalArgumentException(IllegalArgumentException e) {
        return ResponseEntity
                .status(422)
                .body(e.getMessage());
    }

    @ExceptionHandler(exception = StaleDataException.class)
    public ResponseEntity<Serializable> handleStaleDataException(StaleDataException e) {
        return ResponseEntity
                .status(500)
                .body(new Response(e.getCode().name(), e.getMessage()));
    }

    private record Response(String code, String message) implements Serializable {}
}
