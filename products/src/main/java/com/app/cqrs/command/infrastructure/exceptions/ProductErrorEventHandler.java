package com.app.cqrs.command.infrastructure.exceptions;

import java.util.logging.Logger;

import org.axonframework.eventhandling.EventMessage;
import org.axonframework.eventhandling.EventMessageHandler;
import org.axonframework.eventhandling.ListenerInvocationErrorHandler;

public class ProductErrorEventHandler implements ListenerInvocationErrorHandler {

    private final Logger LOGGER = Logger.getLogger(ProductErrorEventHandler.class.getName());

    @Override
    public void onError(Exception exception, EventMessage<?> event, EventMessageHandler eventHandler) throws Exception {
        LOGGER.severe("Error occurred while handling event: " + event.getPayloadType().getName());
        throw exception;
    }

}
