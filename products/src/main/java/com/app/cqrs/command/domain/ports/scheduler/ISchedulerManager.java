package com.app.cqrs.command.domain.ports.scheduler;

import java.time.Duration;

public interface ISchedulerManager {

    <T> void schedule(Duration triggerDateTime, String deadlineName, T messageOrPayload);

}
