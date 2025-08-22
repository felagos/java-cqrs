package com.app.cqrs.shared.domain.aggregates;

import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;

/**
 * Base class for event-sourced aggregates in the application's CQRS/ES model.
 *
 * <p>Provides a minimal, reusable contract for aggregates that are reconstituted
 * from events. Concrete aggregate implementations should extend this class and
 * implement the {@code on(T event)} event sourcing handler to apply events to
 * the aggregate's state.</p>
 *
 * @param <T> the event type (or a common super-type) handled by the aggregate
 */
public abstract class BaseAggregate<T> {

    /**
     * The aggregate identifier. Marked with Axon's {@link AggregateIdentifier}
     * so the framework can associate events with this aggregate instance.
     */
    @AggregateIdentifier
    protected String id;

    /**
     * Protected no-arg constructor required by some serializers and frameworks.
     * Use this when the aggregate will be reconstituted from events.
     */
    public BaseAggregate() {
    }

    /**
     * Create a new aggregate with the provided identifier.
     *
     * @param id the aggregate identifier to assign
     */
    public BaseAggregate(String id) {
        this.id = id;
    }

    /**
     * Returns the aggregate identifier.
     *
     * @return the aggregate id or {@code null} if not yet assigned
     */
    public String getId() {
        return id;
    }

    /**
     * Event sourcing handler entry point used to apply events to this aggregate's state.
     *
     * <p>Concrete aggregates should implement this method and inspect the runtime
     * type of the supplied event (or use multiple overloaded {@code on(...)} methods)
     * to mutate the aggregate's fields accordingly. The method is annotated with
     * Axon's {@link EventSourcingHandler} so the framework will call it while
     * rehydrating aggregate instances from the event store.</p>
     *
     * @param event the event instance to apply; implementations should handle {@code null} defensively if needed
     */
    @EventSourcingHandler
    public abstract void on(T event);

    /**
     * Returns whether this aggregate has been initialized (i.e. an id has been set).
     *
     * @return {@code true} when {@link #id} is non-null, otherwise {@code false}
     */
    protected boolean isInitialized() {
        return id != null;
    }
}
