package com.app.cqrs.command.domain.events.orders;

import java.util.logging.Logger;

import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

import com.app.cqrs.command.domain.ports.orders.IOrderRepository;
import com.app.cqrs.shared.constants.ProcessGroups;

@Component
@ProcessingGroup(ProcessGroups.ORDER_GROUP)
public class OrderEventsHandler {

    private final Logger LOGGER = Logger.getLogger(OrderEventsHandler.class.getName());

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
