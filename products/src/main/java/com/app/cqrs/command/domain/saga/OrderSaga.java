package com.app.cqrs.command.domain.saga;

import java.util.logging.Logger;
import org.axonframework.commandhandling.CommandCallback;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.beans.factory.annotation.Autowired;
import com.app.cqrs.command.domain.events.OrderCreatedEvent;
import com.app.cqrs.command.domain.events.ProductReservedEvent;
import com.app.cqrs.command.domain.ports.IOrderCommandPort;
import com.app.cqrs.command.domain.services.EmailService;
import com.app.cqrs.command.infrastructure.mappers.OrderMapper;
import com.app.cqrs.shared.constants.ProcessGroups;
import com.app.cqrs.shared.domain.commands.ReserveProductCommand;

@Saga
@ProcessingGroup(ProcessGroups.ORDER_GROUP)
public class OrderSaga {

    private transient Logger logger;

    @Autowired
    private transient IOrderCommandPort orderCommandPort;

    @Autowired
    private transient OrderMapper orderMapper;

    @Autowired
    private transient EmailService emailService;

    public OrderSaga() {
        this.logger = getLogger();
    }

    private Logger getLogger() {
        if (logger == null) {
            logger = Logger.getLogger(OrderSaga.class.getName());
        }
        return logger;
    }

    @StartSaga
    @SagaEventHandler(associationProperty = "orderId")
    public void handle(OrderCreatedEvent event) {
        getLogger().info("Starting saga for order: " + event.getOrderId() + " with product: " + event.getProductId());

        var reservedProduct = orderMapper.toReservationCommand(event);

        CommandCallback<ReserveProductCommand, Object> callback = (commandMessage, commandResultMessage) -> {
            if (commandResultMessage.isExceptional()) {
                var exception = commandResultMessage.optionalExceptionResult().get();
                var message = exception.getMessage();
                getLogger().severe(
                        "Failed to reserve product: " + message + " for command: " + commandMessage.getPayload());
                getLogger().severe("Exception type: " + exception.getClass().getSimpleName());
            } else {
                getLogger().info("Successfully reserved product: " + commandMessage.getPayload());
            }
        };

        getLogger().info("Sending reservation command for product: " + reservedProduct.getProductId());
        this.orderCommandPort.sendReservation(reservedProduct, callback);
    }

    @SagaEventHandler(associationProperty = "orderId")
    public void handle(ProductReservedEvent event) {
        getLogger().info("Product reserved for order: " + event.getOrderId());
    }

}
