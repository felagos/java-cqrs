package com.app.cqrs.shared.infrastructure.exceptions;

import java.time.LocalDateTime;

public class ErrorMessage<T> {

    private T error;
    private LocalDateTime timestamp;

    public ErrorMessage(T error) {
        this.error = error;
        this.timestamp = LocalDateTime.now();
    }

    public T getError() {
        return error;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

}