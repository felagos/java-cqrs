package com.app.cqrs.command.domain.events;

import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

import com.app.cqrs.command.domain.ports.IOrderRepository;

@Component
@ProcessingGroup("order-group")
public class OrderEventsHandler {

    private final IOrderRepository orderRepository;

    public OrderEventsHandler(IOrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @EventHandler
    public void on(OrderCreatedEvent event) {
        orderRepository.createOrder(event);
    }

}
