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

    private final Logger logger = Logger.getLogger(OrderSaga.class.getName());

    @Autowired
    private transient IOrderCommandPort orderCommandPort;

    @Autowired
    private transient OrderMapper orderMapper;

    @Autowired
    private transient EmailService emailService;

    public OrderSaga() {
    }

    @StartSaga
    @SagaEventHandler(associationProperty = "orderId")
    public void handle(OrderCreatedEvent event) {
        logger.info("Starting saga for order: " + event.getOrderId() + " with product: " + event.getProductId());

        var reservedProduct = orderMapper.toReservationCommand(event);

        CommandCallback<ReserveProductCommand, Object> callback = (commandMessage, commandResultMessage) -> {
            if (commandResultMessage.isExceptional()) {
                var exception = commandResultMessage.optionalExceptionResult().get();
                var message = exception.getMessage();
                logger.severe("Failed to reserve product: " + message + " for command: " + commandMessage.getPayload());
                logger.severe("Exception type: " + exception.getClass().getSimpleName());
            } else {
                logger.info("Successfully reserved product: " + commandMessage.getPayload());
            }
        };

        logger.info("Sending reservation command for product: " + reservedProduct.getProductId());
        this.orderCommandPort.sendReservation(reservedProduct, callback);
    }

    @SagaEventHandler(associationProperty = "orderId")
    public void handle(ProductReservedEvent event) {
        logger.info("Product reserved for order: " + event.getOrderId());
        
        try {
            emailService.sendProductReservationEmail(event);
            logger.info("Confirmation email sent for order: " + event.getOrderId());
        } catch (Exception e) {
            logger.severe("Failed to send confirmation email for order: " + event.getOrderId() + ". Error: " + e.getMessage());
        }
    }

}
