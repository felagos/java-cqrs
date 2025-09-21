package com.app.cqrs.command.infrastructure.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.cqrs.command.application.EventReplayService;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/management")
public class EventsReplayController {

    private final EventReplayService eventReplayService;

    public EventsReplayController(EventReplayService eventReplayService) {
        this.eventReplayService = eventReplayService;
    }

    @PostMapping("/{processorName}/reset")
    public void replayEvents(@PathVariable String processorName) {
        this.eventReplayService.resetEventProcessor(processorName);
    }

}
