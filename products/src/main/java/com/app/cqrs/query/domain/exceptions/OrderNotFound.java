package com.app.cqrs.query.domain.exceptions;

public class OrderNotFound extends RuntimeException {

    public OrderNotFound(String message) {
        super(message);
    }

}
