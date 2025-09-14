package com.app.cqrs.command.domain.saga;

import java.util.logging.Logger;
import org.axonframework.commandhandling.CommandCallback;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.beans.factory.annotation.Autowired;
import com.app.cqrs.command.domain.events.orders.OrderCreatedEvent;
import com.app.cqrs.command.domain.events.products.ProductReservedEvent;
import com.app.cqrs.command.domain.ports.IOrderCommandPort;
import com.app.cqrs.command.domain.services.EmailService;
import com.app.cqrs.command.infrastructure.mappers.OrderMapper;
import com.app.cqrs.command.infrastructure.mappers.ProductMapper;
import com.app.cqrs.shared.constants.ProcessGroups;
import com.app.cqrs.shared.domain.PaymentDetails;
import com.app.cqrs.shared.domain.commands.ProcessPaymentCommand;
import com.app.cqrs.shared.domain.commands.ReserveProductCommand;
import com.app.cqrs.shared.domain.ports.IUserPaymentDetailGateway;
import com.app.cqrs.shared.domain.query.FetchUserPaymentDetailsQuery;
import com.app.cqrs.shared.utils.IdGenerator;

@Saga
@ProcessingGroup(ProcessGroups.ORDER_GROUP)
public class OrderSaga {

    private final ProductMapper commandProductMapper;

    private transient Logger logger;

    @Autowired
    private transient IOrderCommandPort orderCommandPort;

    @Autowired
    private transient OrderMapper orderMapper;

    @Autowired
    private transient EmailService emailService;

    @Autowired
    private transient IUserPaymentDetailGateway userPaymentDetailGateway;

    public OrderSaga(ProductMapper commandProductMapper) {
        this.commandProductMapper = commandProductMapper;
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
        this.getLogger()
                .info("Starting saga for order: " + event.getOrderId() + " with product: " + event.getProductId());

        var reservedProduct = orderMapper.toReservationCommand(event);

        CommandCallback<ReserveProductCommand, Object> callback = (commandMessage, commandResultMessage) -> {
            if (commandResultMessage.isExceptional()) {
                var exception = commandResultMessage.optionalExceptionResult().get();
                var message = exception.getMessage();
                this.getLogger().severe(
                        "Failed to reserve product: " + message + " for command: " + commandMessage.getPayload());
                this.getLogger().severe("Exception type: " + exception.getClass().getSimpleName());
            } else {
                this.getLogger().info("Successfully reserved product: " + commandMessage.getPayload());
            }
        };

        this.getLogger().info("Sending reservation command for product: " + reservedProduct.getProductId());
        this.orderCommandPort.sendReservation(reservedProduct, callback);
    }

    @SagaEventHandler(associationProperty = "orderId")
    public void handle(ProductReservedEvent event) {
        this.getLogger().info("Product reserved for order: " + event.getOrderId());

        var productDetailsQuery = new FetchUserPaymentDetailsQuery(event.getUserId());

        var userDetails = this.userPaymentDetailGateway.findUserByPaymentDetails(productDetailsQuery);

        if (userDetails.isEmpty()) {
            this.getLogger().severe("User details not found for userId: " + event.getUserId());
            return;
        } else {
            this.getLogger().info("User details found for userId: " + event.getUserId());

            var processPaymentCommand = new ProcessPaymentCommand(IdGenerator.Uuid(), event.getOrderId(),
                    userDetails.get().getPaymentDetails());

            var paymentResponse = this.orderCommandPort.sendPayment(processPaymentCommand);

            this.getLogger()
                    .info("Payment processed for order: " + event.getOrderId() + " with response: " + paymentResponse);
        }
    }

}
