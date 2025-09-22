package com.app.cqrs.command.infrastructure.adapters.scheduler;

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
    public <T> String schedule(Duration triggerDateTime, String deadlineName, T messageOrPayload) {
        return this.deadLineManager.schedule(triggerDateTime, deadlineName, messageOrPayload);
    }

    @Override
    public void cancelAllDeadline(String deadlineName, String scheduleId) {
        this.deadLineManager.cancelSchedule(deadlineName, scheduleId);
    }

}
