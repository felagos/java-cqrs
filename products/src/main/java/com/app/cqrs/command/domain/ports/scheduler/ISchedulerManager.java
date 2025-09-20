package com.app.cqrs.command.domain.ports.scheduler;

import java.time.Instant;

public interface ISchedulerManager {

    <T> void schedule(Instant triggerDateTime, String deadlineName, T messageOrPayload);

}
