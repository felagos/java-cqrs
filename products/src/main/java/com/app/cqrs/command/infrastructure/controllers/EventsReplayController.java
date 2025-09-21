package com.app.cqrs.command.infrastructure.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.cqrs.command.application.EventReplayService;

import org.springframework.boot.actuate.web.exchanges.HttpExchange.Response;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<String> replayEvents(@PathVariable String processorName) {
        var response = this.eventReplayService.resetEventProcessor(processorName);

        if(response) return ResponseEntity.ok().body("The event processor has been reset.");
        else return ResponseEntity.badRequest().body("Failed to reset the event processor.");
    }

}
