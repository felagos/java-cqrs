package com.app.cqrs.command.application;

import com.app.cqrs.command.domain.ports.EventProcessingPort;
import org.springframework.stereotype.Service;

@Service
public class EventReplayService {

    private final EventProcessingPort eventProcessingPort;

    public EventReplayService(EventProcessingPort eventProcessingPort) {
        this.eventProcessingPort = eventProcessingPort;
    }

    public boolean resetEventProcessor(String processorName) {
        return eventProcessingPort.resetEventProcessor(processorName);
    }

}
