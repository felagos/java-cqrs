package com.app.cqrs.command.domain.ports;

import com.app.cqrs.command.domain.Order;
import com.app.cqrs.command.domain.events.OrderCreatedEvent;

public interface IOrderRepository {

    Order createOrder(OrderCreatedEvent event);

}
