package com.example.lab1.Exception;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ErrorHandlingController {
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> onConstraintValidationException(ConstraintViolationException e) {
        return new ResponseEntity<>(new Violation("Bad request", 400), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> onNotFoundException(NotFoundException e) {
        return new ResponseEntity<>(new Violation(e.getMessage(), e.getStatus()), HttpStatus.NOT_FOUND);
    }


}
