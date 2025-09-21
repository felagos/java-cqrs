package com.app.cqrs.shared.infrastructure.exceptions;

public class OrderNotFound extends RuntimeException {

    public OrderNotFound(String message) {
        super(message);
    }

}