package com.app.cqrs.shared.infrastructure.gateways;

import java.util.function.Predicate;

import org.axonframework.queryhandling.QueryUpdateEmitter;
import org.springframework.stereotype.Component;

import com.app.cqrs.shared.domain.ports.query.IQueryUpdateEmitterPort;

/**
 * Gateway implementation that wraps Axon Framework's QueryUpdateEmitter
 * to provide query update emission capabilities while maintaining 
 * clean architecture principles.
 */
@Component
public class QueryUpdateEmitterGateway implements IQueryUpdateEmitterPort {
    
    private final QueryUpdateEmitter queryUpdateEmitter;
    
    public QueryUpdateEmitterGateway(QueryUpdateEmitter queryUpdateEmitter) {
        this.queryUpdateEmitter = queryUpdateEmitter;
    }
    
    @Override
    public <Q, U> void emit(Class<Q> queryType, Predicate<? super Q> predicate, U update) {
        queryUpdateEmitter.emit(queryType, predicate, update);
    }
}