package com.app.cqrs.command.infrastructure.scheduler;

import java.time.Duration;
import org.axonframework.deadline.DeadlineManager;
import org.springframework.stereotype.Component;

import com.app.cqrs.command.domain.ports.scheduler.ISchedulerManager;

@Component
public class SchedulerManager implements ISchedulerManager {

    private final DeadlineManager deadLineManager;

    public SchedulerManager(DeadlineManager deadLineManager) {
        this.deadLineManager = deadLineManager;
    }

    @Override
    public <T> void schedule(Duration triggerDateTime, String deadlineName, T messageOrPayload) {
        this.deadLineManager.schedule(triggerDateTime, deadlineName, messageOrPayload);
    }

}
