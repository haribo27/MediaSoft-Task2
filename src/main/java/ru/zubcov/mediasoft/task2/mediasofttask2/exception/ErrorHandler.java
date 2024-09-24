package ru.zubcov.mediasoft.task2.mediasofttask2.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import ru.zubcov.mediasoft.task2.mediasofttask2.model.ErrorResponse;

import java.time.format.DateTimeParseException;

@RestControllerAdvice
@Slf4j
public class ErrorHandler {

    @ExceptionHandler
    public ResponseEntity<?> handleEntityAlreadyExists(EntityAlreadyExists e) {
        return new ResponseEntity<>(new ErrorResponse(409, e.getMessage()),
                HttpStatus.CONFLICT);
    }

    @ExceptionHandler
    public ResponseEntity<?> handleEntityNotFound(EntityNotFound e) {
        return new ResponseEntity<>(new ErrorResponse(404, e.getMessage()),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class,
            EqualsTeamException.class, MethodArgumentTypeMismatchException.class})
    public ResponseEntity<?> handleMethodArgumentNotValid(Exception e) {
        return new ResponseEntity<>(new ErrorResponse(400, e.getMessage()),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<?> handleDateTimeParseException(DateTimeParseException e) {
        return new ResponseEntity<>(new ErrorResponse(400, "Некорректный формат даты"),
                HttpStatus.BAD_REQUEST);
    }
}
