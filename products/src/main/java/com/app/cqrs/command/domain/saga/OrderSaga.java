package com.app.cqrs.command.domain.saga;

import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.stereotype.Component;

import com.app.cqrs.command.domain.ports.IProductCommandPort;

@Saga
@Component
public class OrderSaga {

    private final transient IProductCommandPort productCommandPort;

    public OrderSaga(IProductCommandPort productCommandPort) {
        this.productCommandPort = productCommandPort;
    }

    @StartSaga
    @SagaEventHandler(associationProperty = "orderId")
    public void handle() {
    }

}
