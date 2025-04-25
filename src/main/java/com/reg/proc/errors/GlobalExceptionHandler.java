package com.reg.proc.errors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {Exception.class})
    protected ResponseEntity<ServerMessageError> handleException(Exception e) {
        var message = new ServerMessageError(
                "Server Error",
                e.getMessage(),
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(message);
    }

    @ExceptionHandler(value = {IllegalArgumentException.class, MethodArgumentNotValidException.class})
    protected ResponseEntity<ServerMessageError> handleBadRequest(Exception e) {
        var message = new ServerMessageError(
                "Bad request",
                e.getMessage(),
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(message);
    }

    @ExceptionHandler(value = {NoSuchElementException.class})
    protected ResponseEntity<ServerMessageError> handleNotFound(Exception e) {
        var message = new ServerMessageError(
                "Not found exception",
                e.getMessage(),
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(message);
    }

}
