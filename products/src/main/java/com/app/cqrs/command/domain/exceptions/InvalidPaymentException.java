package com.app.cqrs.command.domain.exceptions;

public class InvalidPaymentException extends RuntimeException {
    
    public InvalidPaymentException(String message) {
        super(message);
    }

}
