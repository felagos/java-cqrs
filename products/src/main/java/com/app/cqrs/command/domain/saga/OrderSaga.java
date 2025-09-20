package com.app.cqrs.command.domain.saga;

import java.util.logging.Logger;
import org.axonframework.commandhandling.CommandCallback;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.modelling.saga.EndSaga;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.SagaLifecycle;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.beans.factory.annotation.Autowired;
import com.app.cqrs.command.application.OrderCommandService;
import com.app.cqrs.command.domain.commands.ApproveOrderCommand;
import com.app.cqrs.command.domain.events.orders.OrderApprovedEvent;
import com.app.cqrs.command.domain.events.orders.OrderCreatedEvent;
import com.app.cqrs.command.domain.events.payments.PaymentProcessedEvent;
import com.app.cqrs.command.domain.events.products.ProductReservedEvent;
import com.app.cqrs.command.domain.ports.orders.IOrderCommandPort;
import com.app.cqrs.command.infrastructure.mappers.OrderMapper;
import com.app.cqrs.shared.constants.ProcessGroups;
import com.app.cqrs.shared.domain.commands.ProcessPaymentCommand;
import com.app.cqrs.shared.domain.commands.ReserveProductCommand;
import com.app.cqrs.shared.domain.ports.IUserPaymentDetailGateway;
import com.app.cqrs.shared.domain.query.FetchUserPaymentDetailsQuery;
import com.app.cqrs.shared.utils.IdGenerator;
import com.app.cqrs.command.domain.ports.email.IEmailPort;
import com.app.cqrs.shared.utils.EmailContentBuilder;

@Saga
@ProcessingGroup(ProcessGroups.ORDER_GROUP)
public class OrderSaga {

    private final OrderCommandService orderCommandService;

    private transient Logger logger;

    @Autowired
    private transient IOrderCommandPort orderCommandPort;

    @Autowired
    private transient OrderMapper orderMapper;

    @Autowired
    private transient IUserPaymentDetailGateway userPaymentDetailGateway;

    @Autowired
    private transient IEmailPort emailService;

    public OrderSaga(OrderCommandService orderCommandService) {
        this.orderCommandService = orderCommandService;
    }

    private Logger getLogger() {
        if (logger == null) {
            logger = Logger.getLogger(OrderSaga.class.getName());
        }
        return logger;
    }

    @StartSaga
    @SagaEventHandler(associationProperty = "orderId")
    public void onOrderCreated(OrderCreatedEvent event) {
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
                
                // End saga as product reservation failed
                SagaLifecycle.end();
            } else {
                this.getLogger().info("Successfully reserved product: " + commandMessage.getPayload());
            }
        };

        this.getLogger().info("Sending reservation command for product: " + reservedProduct.getProductId());
        this.orderCommandPort.sendReservation(reservedProduct, callback);
    }

    @SagaEventHandler(associationProperty = "orderId")
    public void onProductReserved(ProductReservedEvent event) {
        this.getLogger().info("Product reserved for order: " + event.getOrderId());

        var productDetailsQuery = new FetchUserPaymentDetailsQuery(event.getUserId());

        var userDetails = this.userPaymentDetailGateway.findUserByPaymentDetails(productDetailsQuery);

        this.getLogger().info("Fetching user details for userId: " + event.getUserId());
        this.getLogger().info("Using query: " + productDetailsQuery);
        this.getLogger().info("Fetched user details: " + userDetails);

        if (userDetails.isEmpty()) {
            this.getLogger().severe("User details not found for userId: " + event.getUserId());

            this.cancelReservation(event, "User details not found");

            return;
        } else {
            this.getLogger().info("User details found for userId: " + event.getUserId());

            var processPaymentCommand = new ProcessPaymentCommand(IdGenerator.Uuid(), event.getOrderId(),
                    userDetails.get().getPaymentDetails());

            CommandCallback<ProcessPaymentCommand, Object> paymentCallback = (commandMessage, commandResultMessage) -> {
                if (commandResultMessage.isExceptional()) {
                    var exception = commandResultMessage.optionalExceptionResult().get();
                    var message = exception.getMessage();
                    this.getLogger().severe(
                            "Failed to process payment: " + message + " for command: " + commandMessage.getPayload());
                    this.getLogger().severe("Exception type: " + exception.getClass().getSimpleName());

                    this.cancelReservation(event, "Payment processing failed: " + message);
                } else {
                    this.getLogger().info("Successfully sent payment command: " + commandMessage.getPayload());
                }
            };

            this.orderCommandPort.sendPaymentAsync(processPaymentCommand, paymentCallback);

            this.getLogger().info("Payment command sent for order: " + event.getOrderId());
        }
    }

    @SagaEventHandler(associationProperty = "orderId")
    public void onPayment(PaymentProcessedEvent processedEvent) {
        this.getLogger().info("Payment processed for order: " + processedEvent.getOrderId());

        var approvedOrder = new ApproveOrderCommand(processedEvent.getOrderId());

        CommandCallback<ApproveOrderCommand, Object> approvalCallback = (commandMessage, commandResultMessage) -> {
            if (commandResultMessage.isExceptional()) {
                var exception = commandResultMessage.optionalExceptionResult().get();
                var message = exception.getMessage();
                this.getLogger().severe(
                        "Failed to approve order: " + message + " for command: " + commandMessage.getPayload());
                this.getLogger().severe("Exception type: " + exception.getClass().getSimpleName());
                
                // Note: We need the original ProductReservedEvent to cancel reservation
                // This would require storing the event or implementing a different cancellation approach
                this.getLogger().severe("Order approval failed - manual intervention required for order: " + processedEvent.getOrderId());
            } else {
                this.getLogger().info("Successfully sent order approval command: " + commandMessage.getPayload());
            }
        };

        this.orderCommandPort.sendApprovedPaymentAsync(approvedOrder, approvalCallback);

        this.getLogger().info("Order approval command sent for order: " + processedEvent.getOrderId());
    }

    @EndSaga
    @SagaEventHandler(associationProperty = "orderId")
    public void onApprovedOrder(OrderApprovedEvent approvedEvent) {
        this.getLogger().info("Order approved for order: " + approvedEvent.getOrderId());

        try {
            String subject = EmailContentBuilder.buildOrderConfirmationSubject(approvedEvent.getOrderId());
            String body = EmailContentBuilder.buildOrderConfirmationBody(approvedEvent.getOrderId());

            emailService.sendEmail("customer@example.com", subject, body);

            this.getLogger().info("Confirmation email sent for order: " + approvedEvent.getOrderId());
        } catch (Exception e) {
            this.getLogger().severe("Failed to send confirmation email for order: " + approvedEvent.getOrderId()
                    + ". Error: " + e.getMessage());
        }

        this.getLogger().info("Ending saga for order: " + approvedEvent.getOrderId());

        SagaLifecycle.end();
    }

    private void cancelReservation(ProductReservedEvent event, String reason) {
        var cancelProduct = this.orderMapper.toCancelReservation(event, reason);

        this.orderCommandPort.sendCancelReservation(cancelProduct);

  
    }

}
