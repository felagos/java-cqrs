package com.app.cqrs.command.application;

import org.springframework.stereotype.Service;

import com.app.cqrs.command.domain.ports.replay.EventProcessingPort;

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
