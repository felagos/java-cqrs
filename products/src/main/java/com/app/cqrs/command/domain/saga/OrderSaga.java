package com.app.cqrs.command.domain.saga;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.axonframework.commandhandling.CommandCallback;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.deadline.annotation.DeadlineHandler;
import org.axonframework.modelling.saga.EndSaga;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.SagaLifecycle;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.beans.factory.annotation.Autowired;
import com.app.cqrs.shared.domain.ports.query.IQueryUpdateEmitterPort;
import com.app.cqrs.shared.domain.commands.orders.ApproveOrderCommand;
import com.app.cqrs.command.domain.commands.CancelProductReservationCommand;
import com.app.cqrs.shared.domain.events.orders.OrderApprovedEvent;
import com.app.cqrs.shared.domain.events.orders.OrderCreatedEvent;
import com.app.cqrs.shared.domain.events.orders.RejectOrderEvent;
import com.app.cqrs.command.domain.events.payments.PaymentProcessedEvent;
import com.app.cqrs.shared.domain.events.products.ProductReservedEvent;
import com.app.cqrs.shared.domain.orders.Order;
import com.app.cqrs.shared.domain.ports.orders.IOrderCommandPort;
import com.app.cqrs.command.domain.ports.scheduler.ISchedulerManager;
import com.app.cqrs.shared.constants.ProcessGroups;
import com.app.cqrs.shared.domain.commands.ProcessPaymentCommand;
import com.app.cqrs.shared.domain.commands.ReserveProductCommand;
import com.app.cqrs.shared.domain.ports.IUserPaymentDetailGateway;
import com.app.cqrs.shared.domain.query.FetchUserPaymentDetailsQuery;
import com.app.cqrs.shared.domain.query.orders.FindOrderQuery;
import com.app.cqrs.shared.infrastructure.mappers.OrderMapper;
import com.app.cqrs.shared.utils.IdGenerator;
import com.app.cqrs.command.domain.ports.email.IEmailPort;
import com.app.cqrs.shared.utils.EmailContentBuilder;

@Saga
@ProcessingGroup(ProcessGroups.ORDER_GROUP)
public class OrderSaga {

    private static final Logger logger = LoggerFactory.getLogger(OrderSaga.class);

    @Autowired
    private transient IOrderCommandPort orderCommandPort;

    @Autowired
    private transient OrderMapper orderMapper;

    @Autowired
    private transient IUserPaymentDetailGateway userPaymentDetailGateway;

    @Autowired
    private transient IEmailPort emailService;

    @Autowired
    private transient ISchedulerManager schedulerManager;

    @Autowired
    private transient IQueryUpdateEmitterPort queryUpdateEmitter;

    private String deadlineId;
    private String orderId;
    private String productId;
    private String userId;
    private int quantity;
    private String addressId;

    public OrderSaga() {
    }

    private final String PAYMENT_DEADLINE = "payment-deadline-process";

    @StartSaga
    @SagaEventHandler(associationProperty = "orderId")
    public void onOrderCreated(OrderCreatedEvent event) {
        logger.info("Starting saga for order: {} with product: {}", event.getOrderId(), event.getProductId());

        // Store order details for later use in query updates
        this.orderId = event.getOrderId();
        this.productId = event.getProductId();
        this.userId = event.getUserId();
        this.quantity = event.getQuantity();
        this.addressId = event.getAddressId();

        var reservedProduct = orderMapper.toReservationCommand(event);

        CommandCallback<ReserveProductCommand, Object> callback = (commandMessage, commandResultMessage) -> {
            if (commandResultMessage.isExceptional()) {
                var exception = commandResultMessage.optionalExceptionResult().get();
                var message = exception.getMessage();

                logger.error("Failed to reserve product: {} for command: {}", message, commandMessage.getPayload());
                logger.error("Exception type: {}", exception.getClass().getSimpleName());

                var rejectOrder = this.orderMapper.toRejectOrderCommand(event.getOrderId(),
                        "Product reservation failed: " + message);
                this.orderCommandPort.sendSync(rejectOrder);
            } else {
                logger.info("Successfully reserved product: {}", commandMessage.getPayload());
            }
        };

        logger.info("Sending reservation command for product: {}", reservedProduct.getProductId());
        this.orderCommandPort.send(reservedProduct, callback);
    }

    @SagaEventHandler(associationProperty = "orderId")
    public void onProductReserved(ProductReservedEvent event) {
        logger.info("OrderSaga: Received ProductReservedEvent for order: {}", event.getOrderId());
        logger.info("Product reserved for order: {}", event.getOrderId());

        var productDetailsQuery = new FetchUserPaymentDetailsQuery(event.getUserId());

        var userDetails = this.userPaymentDetailGateway.findUserByPaymentDetails(productDetailsQuery);

        logger.info("Fetching user details for userId: {}", event.getUserId());
        logger.info("Using query: {}", productDetailsQuery);
        logger.info("Fetched user details: {}", userDetails);

        if (userDetails.isEmpty()) {
            logger.error("User details not found for userId: {}", event.getUserId());

            this.cancelReservation(event, "User details not found");

            return;
        } else {
            logger.info("User details found for userId: {}", event.getUserId());

            /* this.deadlineId = this.schedulerManager.schedule(
                    Duration.ofSeconds(10),
                    PAYMENT_DEADLINE,
                    event); */

            var processPaymentCommand = new ProcessPaymentCommand(IdGenerator.Uuid(), event.getOrderId(),
                    userDetails.get().getPaymentDetails());

            CommandCallback<ProcessPaymentCommand, Object> paymentCallback = (commandMessage, commandResultMessage) -> {
                if (commandResultMessage.isExceptional()) {
                    var exception = commandResultMessage.optionalExceptionResult().get();
                    var message = exception.getMessage();
                    logger.error("Failed to process payment: {} for command: {}", message, commandMessage.getPayload());
                    logger.error("Exception type: {}", exception.getClass().getSimpleName());

                    this.cancelReservation(event, "Payment processing failed: " + message);
                } else {
                    logger.info("Successfully sent payment command: {}", commandMessage.getPayload());
                }
            };

            this.orderCommandPort.send(processPaymentCommand, paymentCallback);

            logger.info("Payment command sent for order: {}", event.getOrderId());
        }
    }

    @SagaEventHandler(associationProperty = "orderId")
    public void onPaymentProcess(PaymentProcessedEvent processedEvent) {
        logger.info("Payment processed for order: {}", processedEvent.getOrderId());

        this.cancelDeadline();

        var approvedOrder = new ApproveOrderCommand(processedEvent.getOrderId());
        logger.info("OrderSaga: About to send ApproveOrderCommand: {}", approvedOrder);

        CommandCallback<ApproveOrderCommand, Object> approvalCallback = (commandMessage, commandResultMessage) -> {
            if (commandResultMessage.isExceptional()) {
                var exception = commandResultMessage.optionalExceptionResult().get();
                var message = exception.getMessage();
                logger.error("Failed to approve order: {} for command: {}", message, commandMessage.getPayload());
                logger.error("Exception type: {}", exception.getClass().getSimpleName());

                logger.error("Order approval failed - manual intervention required for order: {}",
                        processedEvent.getOrderId());
            } else {
                logger.info("Successfully sent order approval command: {}", commandMessage.getPayload());
            }
        };

        this.orderCommandPort.send(approvedOrder, approvalCallback);

        logger.info("Order approval command sent for order: {}", processedEvent.getOrderId());
    }

    @SagaEventHandler(associationProperty = "orderId")
    public void onCancelProductReservation(CancelProductReservationCommand event) {
        logger.info("Cancelling product reservation for order: {}", event.getOrderId());

        var rejectOrder = this.orderMapper.toRejectOrderCommand(event.getOrderId(), event.getReason());

        this.orderCommandPort.sendSync(rejectOrder);
    }

    @EndSaga
    @SagaEventHandler(associationProperty = "orderId")
    public void onApprovedOrder(OrderApprovedEvent approvedEvent) {
        logger.info("OrderSaga: Received OrderApprovedEvent for order: {}", approvedEvent.getOrderId());
        logger.info("Order approved for order: {}", approvedEvent.getOrderId());

        try {
            String subject = EmailContentBuilder.buildOrderConfirmationSubject(approvedEvent.getOrderId());
            String body = EmailContentBuilder.buildOrderConfirmationBody(approvedEvent.getOrderId());

            emailService.sendEmail("customer@example.com", subject, body);

            logger.info("Confirmation email sent for order: {}", approvedEvent.getOrderId());
        } catch (Exception e) {
            logger.error("Failed to send confirmation email for order: {}. Error: {}",
                    approvedEvent.getOrderId(), e.getMessage());
        }

        logger.info("Ending saga for order: {}", approvedEvent.getOrderId());

        var order = Order.builder()
                .orderId(approvedEvent.getOrderId())
                .productId(this.productId)
                .userId(this.userId)
                .quantity(this.quantity)
                .addressId(this.addressId)
                .orderStatus(approvedEvent.getOrderStatus())
                .build();

        this.queryUpdateEmitter.emit(FindOrderQuery.class, query -> query.getOrderId().equals(approvedEvent.getOrderId()), order);
        logger.info("Order update emitted for order: {}", approvedEvent.getOrderId());

        SagaLifecycle.end();
    }

    @EndSaga
    @SagaEventHandler(associationProperty = "orderId")
    public void onRejectOrder(RejectOrderEvent rejectedEvent) {
        logger.info("Order rejected for order: {}. Reason: {}",
                rejectedEvent.getOrderId(), rejectedEvent.getReason());
        logger.info("Ending saga for order: {}", rejectedEvent.getOrderId());
        
        var order = Order.builder()
                .orderId(rejectedEvent.getOrderId())
                .productId(this.productId)
                .userId(this.userId)
                .quantity(this.quantity)
                .addressId(this.addressId)
                .orderStatus(rejectedEvent.getOrderStatus())
                .build();

        this.queryUpdateEmitter.emit(FindOrderQuery.class, query -> query.getOrderId().equals(rejectedEvent.getOrderId()), order);
        logger.info("Query update emitted for rejected order: {}", rejectedEvent.getOrderId());
        
        SagaLifecycle.end();
    }

    @DeadlineHandler(deadlineName = PAYMENT_DEADLINE)
    public void handlePaymentDeadline(ProductReservedEvent productReservedEvent) {
        logger.info("Handling payment deadline for order: {}", productReservedEvent.getOrderId());

        this.cancelReservation(productReservedEvent, "Payment deadline exceeded");
    }

    private void cancelDeadline() {
        if (this.deadlineId != null && !this.deadlineId.isEmpty()) {
            //this.schedulerManager.cancelAllDeadline(PAYMENT_DEADLINE, this.deadlineId);
            this.deadlineId = null;
        }
    }

    private void cancelReservation(ProductReservedEvent event, String reason) {
        var cancelProduct = this.orderMapper.toCancelReservation(event, reason);

        this.orderCommandPort.sendSync(cancelProduct);
    }

}
