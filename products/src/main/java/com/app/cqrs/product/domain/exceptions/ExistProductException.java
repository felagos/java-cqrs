package com.app.cqrs.product.domain.exceptions;

public class ExistProductException extends RuntimeException {
    private String message;

    public ExistProductException(String message) {
        super(message);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
