package com.app.cqrs.command.domain.saga;

import java.util.logging.Logger;
import org.axonframework.commandhandling.CommandCallback;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.stereotype.Component;
import com.app.cqrs.command.domain.events.OrderCreatedEvent;
import com.app.cqrs.command.domain.ports.IOrderCommandPort;
import com.app.cqrs.command.infrastructure.mappers.OrderMapper;
import com.app.cqrs.shared.domain.commands.ReservedCommandEvent;

@Saga
@Component
public class OrderSaga {

    private final Logger logger = Logger.getLogger(OrderSaga.class.getName());
    private final transient IOrderCommandPort orderCommandPort;
    private final OrderMapper orderMapper;

    public OrderSaga(IOrderCommandPort orderCommandPort, OrderMapper orderMapper) {
        this.orderCommandPort = orderCommandPort;
        this.orderMapper = orderMapper;
    }

    @StartSaga
    @SagaEventHandler(associationProperty = "orderId")
    public void handle(OrderCreatedEvent event) {
        var reservedProduct = orderMapper.toReservationCommand(event);

        CommandCallback<ReservedCommandEvent, Object> callback = (commandMessage, commandResultMessage) -> {
            if (commandResultMessage.isExceptional()) {
                logger.severe("Failed to reserve product: "
                        + commandResultMessage.optionalExceptionResult().get().getMessage());
            } else {
                logger.info("Successfully reserved product: " + commandMessage.getPayload());
            }
        };

        this.orderCommandPort.sendReservation(reservedProduct, callback);
    }

}
