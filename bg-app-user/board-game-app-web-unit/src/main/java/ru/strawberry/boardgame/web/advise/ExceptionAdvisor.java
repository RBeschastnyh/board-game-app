package ru.strawberry.boardgame.web.advise;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.strawberry.boardgame.web.exception.DBException;

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
}
