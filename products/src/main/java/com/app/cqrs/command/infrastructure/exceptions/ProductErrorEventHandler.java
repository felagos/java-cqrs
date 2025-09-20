package com.app.cqrs.command.infrastructure.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.axonframework.eventhandling.EventMessage;
import org.axonframework.eventhandling.EventMessageHandler;
import org.axonframework.eventhandling.ListenerInvocationErrorHandler;

public class ProductErrorEventHandler implements ListenerInvocationErrorHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductErrorEventHandler.class);

    @Override
    public void onError(Exception exception, EventMessage<?> event, EventMessageHandler eventHandler) throws Exception {
        LOGGER.error("Error occurred while handling event: {}", event.getPayloadType().getName());
        throw exception;
    }

}
