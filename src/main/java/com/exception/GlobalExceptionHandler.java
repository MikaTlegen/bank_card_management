package com.exception;

import com.dto.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFound(UserNotFoundException ex) {
        ErrorResponse error = new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage(), LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler({
            EmailAlreadyExistsException.class,
            UsernameAlreadyExistsException.class,
            UserHasActiveCardsException.class
    })
    public ResponseEntity<ErrorResponse> handleConflict(RuntimeException ex) {
        ErrorResponse error = new ErrorResponse(HttpStatus.CONFLICT.value(), ex.getMessage(), LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    @ExceptionHandler(SelfDeletionForbiddenException.class)
    public ResponseEntity<ErrorResponse> handleSelfDeletionForbidden(SelfDeletionForbiddenException ex) {
        ErrorResponse error = new ErrorResponse(HttpStatus.FORBIDDEN.value(), ex.getMessage(), LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
    }
}