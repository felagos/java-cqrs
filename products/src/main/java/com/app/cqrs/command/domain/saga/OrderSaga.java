package com.app.cqrs.command.domain.saga;

import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.stereotype.Component;

import com.app.cqrs.command.domain.events.OrderCreatedEvent;
import com.app.cqrs.command.domain.ports.IOrderCommandPort;

@Saga
@Component
public class OrderSaga {

    private final transient IOrderCommandPort orderCommandPort;

    public OrderSaga(IOrderCommandPort orderCommandPort) {
        this.orderCommandPort = orderCommandPort;
    }

    @StartSaga
    @SagaEventHandler(associationProperty = "orderId")
    public void handle(OrderCreatedEvent event) {
    }

}
