package com.app.cqrs.command.domain.ports.scheduler;

import java.time.Duration;

public interface ISchedulerManager {

    <T> String schedule(Duration triggerDateTime, String deadlineName, T messageOrPayload);

    void cancelAllDeadline(String deadlineName, String scheduleId);

}
