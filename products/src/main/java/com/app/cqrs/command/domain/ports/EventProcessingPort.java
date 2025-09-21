package com.app.cqrs.command.domain.ports;

/**
 * Port interface for event processing operations.
 * This abstraction allows the application layer to remain independent
 * of specific event processing framework implementations.
 */
public interface EventProcessingPort {
    
    /**
     * Resets an event processor by shutting it down, resetting its tokens,
     * and starting it again.
     * 
     * @param processorName the name of the event processor to reset
     * @return true if the processor was found and reset successfully, false otherwise
     */
    boolean resetEventProcessor(String processorName);
}