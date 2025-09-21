package com.app.cqrs.configuration;

import org.axonframework.eventsourcing.EventCountSnapshotTriggerDefinition;
import org.axonframework.eventsourcing.SnapshotTriggerDefinition;
import org.axonframework.eventsourcing.Snapshotter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.app.cqrs.shared.constants.SnapshotDefinition;

@Configuration
public class SnapshotConfig {

    @Bean(name = SnapshotDefinition.SNAPSHOT_TRIGGER_NAME)
    public SnapshotTriggerDefinition snapshotTriggerDefinition(Snapshotter snapshotter) {
        return new EventCountSnapshotTriggerDefinition(snapshotter, SnapshotDefinition.SNAPSHOT_TRIGGER_THRESHOLD);
    }

}
