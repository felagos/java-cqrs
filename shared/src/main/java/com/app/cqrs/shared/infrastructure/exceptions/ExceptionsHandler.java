package com.app.cqrs.shared.infrastructure.exceptions;

import java.util.HashMap;

import org.axonframework.commandhandling.CommandExecutionException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionsHandler {

    @ExceptionHandler(value = { OrderNotFound.class })
    public ResponseEntity<ErrorMessage<String>> handleOrderNotFound(OrderNotFound ex) {
        var error = new ErrorMessage<String>(ex.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(value = { InvalidProductException.class, ExistingProductException.class,
            ReserveProductException.class })
    public ResponseEntity<ErrorMessage<String>> handleInvalidProduct(RuntimeException ex) {
        var error = new ErrorMessage<String>(ex.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<ErrorMessage<HashMap<String, String>>> handleBindException(BindException ex) {
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

        var error = new ErrorMessage<HashMap<String, String>>(errorsFields);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(value = { Exception.class })
    public ResponseEntity<ErrorMessage<String>> handleGenericException(Exception ex) throws Exception {
        var error = new ErrorMessage<String>(ex.getMessage());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

    @ExceptionHandler(value = { CommandExecutionException.class })
    public ResponseEntity<ErrorMessage<String>> handleCommandExecutionException(CommandExecutionException ex) {
        var error = new ErrorMessage<String>(ex.getMessage());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

}