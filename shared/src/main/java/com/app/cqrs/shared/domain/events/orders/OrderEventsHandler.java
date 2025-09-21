package com.app.cqrs.shared.domain.events.orders;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

import com.app.cqrs.shared.domain.ports.orders.IOrderRepository;
import com.app.cqrs.shared.constants.ProcessGroups;

@Component
@ProcessingGroup(ProcessGroups.ORDER_GROUP)
public class OrderEventsHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderEventsHandler.class);

    private final IOrderRepository orderRepository;

    public OrderEventsHandler(IOrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @EventHandler
    public void handleOrderCreatedEvent(OrderCreatedEvent event) {
        LOGGER.info("Handling OrderCreatedEvent for order: " + event);
        this.orderRepository.createOrder(event);
    }

    @EventHandler
    public void handleApprovedOrderEvent(OrderApprovedEvent event) {
        LOGGER.info("Handling OrderApprovedEvent for order: " + event);
        this.orderRepository.updateOrderStatus(event.getOrderId(), event.getOrderStatus());
    }

    @EventHandler
    public void handleRejectOrderEvent(RejectOrderEvent event) {
        LOGGER.info("Handling RejectOrderEvent for order: " + event);
        this.orderRepository.updateOrderStatus(event.getOrderId(), event.getOrderStatus());
    }

}