package com.app.cqrs.command.domain.ports.orders;

import java.util.concurrent.TimeUnit;

import org.axonframework.commandhandling.CommandCallback;

import com.app.cqrs.command.domain.Order;
import com.app.cqrs.command.domain.commands.CreateOrderCommand;

public interface IOrderCommandPort {

    /**
     * Creates an order and returns the Order entity
     */
    Order createOrder(CreateOrderCommand order);
    
    /**
     * Sends a command synchronously and waits for response
     */
    <T> String sendSync(T command);
    
    /**
     * Sends a command synchronously with timeout and waits for response
     */
    <T> String sendSync(T command, long timeout, TimeUnit timeUnit);
    
    /**
     * Sends a command asynchronously with callback
     */
    <T> void send(T command, CommandCallback<T, Object> callback);

}
