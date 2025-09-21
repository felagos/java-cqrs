package com.app.cqrs.shared.infrastructure.exceptions;

public class ExistingProductException extends RuntimeException {
    private String message;

    public ExistingProductException(String message) {
        super(message);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}