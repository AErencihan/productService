package com.example.productservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;


@RestControllerAdvice
public class GenericExceptionHandler {

    @ExceptionHandler(GlobalException.class)
    public ResponseEntity<?> handleException(GlobalException ex) {
        Map<String, Object> errors = new HashMap<>();
        errors.put("error", ex.getMessage());
        return ResponseEntity
                .status(ex.getHttpStatus() != null ? ex.getHttpStatus() : HttpStatus.BAD_REQUEST)
                .body(errors);

    }
}
