package com.app.cqrs.command.domain.exceptions;

public class ReserveProductException extends RuntimeException {
    private String message;

    public ReserveProductException(String message) {
        super(message);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
