package com.app.cqrs.command.domain.ports.orders;

import com.app.cqrs.command.domain.Order;
import com.app.cqrs.command.domain.events.orders.OrderCreatedEvent;

public interface IOrderRepository {

    Order createOrder(OrderCreatedEvent event);

}
