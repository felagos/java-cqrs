package com.app.cqrs.command.infrastructure.replay;

import org.axonframework.config.EventProcessingConfiguration;
import org.axonframework.eventhandling.TrackingEventProcessor;
import org.springframework.stereotype.Component;

import com.app.cqrs.command.domain.ports.replay.EventProcessingPort;

/**
 * Adapter that implements the EventProcessingPort using Axon Framework's
 * EventProcessingConfiguration. This adapter bridges the application layer
 * with the Axon Framework infrastructure.
 */
@Component
public class AxonEventProcessingAdapter implements EventProcessingPort {

    private final EventProcessingConfiguration eventProcessingConfiguration;

    public AxonEventProcessingAdapter(EventProcessingConfiguration eventProcessingConfiguration) {
        this.eventProcessingConfiguration = eventProcessingConfiguration;
    }

    @Override
    public boolean resetEventProcessor(String processorName) {
        var eventProcessor = eventProcessingConfiguration.eventProcessor(processorName, TrackingEventProcessor.class);

        if (eventProcessor.isPresent()) {
            var processor = eventProcessor.get();
            
            processor.shutDown();
            processor.resetTokens();
            processor.start();
            
            return true;
        }
        
        return false;
    }
}