package com.app.cqrs.product.infrastructure.gateways;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

/**
 * A generic wrapper around Axon Framework's CommandGateway that provides 
 * convenient methods for sending commands with different execution patterns.
 * This wrapper supports synchronous, asynchronous, and fire-and-forget command execution.
 * 
 * @param <T> the type of the command parameter
 */
@Component
public class CommandGatewayWrapper<T> {

    private final CommandGateway commandGateway;

    /**
     * Constructs a new CommandGatewayWrapper.
     * 
     * @param commandGateway the Axon Framework CommandGateway to wrap
     */
    public CommandGatewayWrapper(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    /**
     * Synchronously send a command and wait for the result
     * 
     * @param command the command to send
     * @param <R>     the type of the expected result
     * @return the result of the command execution
     */
    public <R> R createCommand(T command) {
        return commandGateway.sendAndWait(command);
    }

    /**
     * Asynchronously send a command
     * 
     * @param command the command to send
     * @param <R>     the type of the expected result
     * @return a CompletableFuture with the result
     */
    public <R> CompletableFuture<R> createCommandAsync(T command) {
        return commandGateway.send(command);
    }

    /**
     * Send a command and fire-and-forget (no return value expected)
     * 
     * @param command the command to send
     */
    public void sendCommand(T command) {
        commandGateway.send(command);
    }

    /**
     * Synchronously send any command and wait for the result
     * 
     * @param command the command to send
     * @param <R>     the type of the expected result
     * @return the result of the command execution
     */
    public <R> R sendAndWait(T command) {
        return commandGateway.sendAndWait(command);
    }

    /**
     * Asynchronously send any command
     * 
     * @param command the command to send
     * @param <R>     the type of the expected result
     * @return a CompletableFuture with the result
     */
    public <R> CompletableFuture<R> sendAsync(T command) {
        return commandGateway.send(command);
    }
}
