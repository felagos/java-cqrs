package com.app.cqrs.command.domain.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ProductServiceErrorHandler {

    @ExceptionHandler(value = { InvalidProductException.class, ExistingProductException.class })
    public ResponseEntity<Map<String, String>> handleInvalidProduct(RuntimeException ex) {
        var error = Map.of("error", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<Map<String, HashMap<String, String>>> handleBindException(BindException ex) {
        var errorsFields = ex.getBindingResult().getAllErrors().stream()
            .reduce(new HashMap<String, String>(), (acc, error) -> {
                String fieldName = ((FieldError) error).getField();
                String errorMessage = error.getDefaultMessage();
                acc.put(fieldName, errorMessage);
                return acc;
            }, (map1, map2) -> {
                map1.putAll(map2);
                return map1;
            });

        var error = Map.of("errors", errorsFields);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(value = { Exception.class })
    public ResponseEntity<Map<String, String>> handleGenericException(Exception ex) {
        var error = Map.of("error", ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

}
