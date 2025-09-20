package com.app.cqrs.command.domain.aggregates;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import com.app.cqrs.command.domain.OrderStatus;
import com.app.cqrs.command.domain.commands.ApproveOrderCommand;
import com.app.cqrs.command.domain.commands.CreateOrderCommand;
import com.app.cqrs.command.domain.commands.RejectOrderCommand;
import com.app.cqrs.command.domain.events.orders.OrderApprovedEvent;
import com.app.cqrs.command.domain.events.orders.OrderCreatedEvent;
import com.app.cqrs.command.domain.events.orders.RejectOrderEvent;

@Aggregate
public class OrderAggregate {

    private static final Logger logger = LoggerFactory.getLogger(OrderAggregate.class);

    @AggregateIdentifier
    private String orderId;
    private String productId;
    private String userId;
    private int quantity;
    private String addressId;
    private OrderStatus orderStatus;

    public OrderAggregate() {
    }

    @CommandHandler
    public OrderAggregate(CreateOrderCommand createOrderCommand) {
        var orderCreatedEvent = new OrderCreatedEvent();

        orderCreatedEvent.setOrderId(createOrderCommand.getOrderId());
        orderCreatedEvent.setProductId(createOrderCommand.getProductId());
        orderCreatedEvent.setUserId(createOrderCommand.getUserId());
        orderCreatedEvent.setQuantity(createOrderCommand.getQuantity());
        orderCreatedEvent.setAddressId(createOrderCommand.getAddressId());
        orderCreatedEvent.setOrderStatus(createOrderCommand.getOrderStatus());

        logger.info("OrderCreatedEvent: " + orderCreatedEvent);

        AggregateLifecycle.apply(orderCreatedEvent);
    }

    @CommandHandler
    public void onApprovedOrder(ApproveOrderCommand approveOrderCommand) {
        var orderApprovedEvent = new OrderApprovedEvent(approveOrderCommand.getOrderId());

        AggregateLifecycle.apply(orderApprovedEvent);
    }

    @CommandHandler
    public void onRejectOrder(RejectOrderCommand rejectOrderCommand) {
        var orderRejectedEvent = RejectOrderEvent.builder()
                .orderId(rejectOrderCommand.getOrderId())
                .reason(rejectOrderCommand.getReason())
                .build();

        AggregateLifecycle.apply(orderRejectedEvent);
    }

    @EventSourcingHandler
    public void handleOrderCreatedEvent(OrderCreatedEvent orderCreatedEvent) {
        this.orderId = orderCreatedEvent.getOrderId();
        this.productId = orderCreatedEvent.getProductId();
        this.userId = orderCreatedEvent.getUserId();
        this.addressId = orderCreatedEvent.getAddressId();
        this.quantity = orderCreatedEvent.getQuantity();
        this.orderStatus = orderCreatedEvent.getOrderStatus();
    }

    @EventSourcingHandler
    public void handleApprovedOrderEvent(OrderCreatedEvent orderCreatedEvent) {
        this.orderId = orderCreatedEvent.getOrderId();
        this.orderStatus = orderCreatedEvent.getOrderStatus();
    }

    @EventSourcingHandler
    public void handleRejectOrderEvent(RejectOrderEvent rejectOrderEvent) {
        this.orderStatus = rejectOrderEvent.getOrderStatus();
    }

}
