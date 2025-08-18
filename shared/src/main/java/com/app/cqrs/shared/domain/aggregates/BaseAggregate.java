package com.app.cqrs.shared.domain.aggregates;

import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;

/**
 * Abstract base class for event-sourced aggregates.
 * Provides common event sourcing functionality that can be inherited by specific aggregates.
 */
public abstract class BaseAggregate<T> {

    @AggregateIdentifier
    protected String id;

    /**
     * Default constructor for aggregate instantiation.
     */
    public BaseAggregate() {
    }

    /**
     * Constructor with aggregate identifier.
     * @param id the aggregate identifier
     */
    public BaseAggregate(String id) {
        this.id = id;
    }

    /**
     * Get the aggregate identifier.
     * @return the aggregate identifier
     */
    public String getId() {
        return id;
    }

    /**
     * Abstract method that must be implemented by concrete aggregates
     * to handle their specific events for state reconstruction.
     * 
     * @param event the event to handle
     */
    @EventSourcingHandler
    public abstract void on(T event);

    /**
     * Utility method to check if the aggregate has been initialized.
     * @return true if the aggregate has an identifier, false otherwise
     */
    protected boolean isInitialized() {
        return id != null && !id.isEmpty();
    }
}
