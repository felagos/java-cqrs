package com.app.cqrs.shared.domain.ports.query;

import java.util.function.Predicate;

/**
 * Port interface for emitting query updates in CQRS architecture.
 * This abstraction allows for decoupling from specific query update emitter implementations.
 */
public interface IQueryUpdateEmitterPort {
    
    /**
     * Emits an update to subscription queries that match the given predicate.
     * 
     * @param <Q> the type of the query
     * @param <U> the type of the update
     * @param queryType the class of the query type to emit updates for
     * @param predicate the predicate to match subscription queries
     * @param update the update object to emit
     */
    <Q, U> void emit(Class<Q> queryType, Predicate<? super Q> predicate, U update);
}