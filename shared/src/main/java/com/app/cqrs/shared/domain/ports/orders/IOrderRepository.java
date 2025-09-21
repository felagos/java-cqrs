package com.app.cqrs.shared.domain.ports.orders;

import com.app.cqrs.shared.domain.orders.Order;
import com.app.cqrs.shared.domain.orders.OrderStatus;

import java.util.Optional;

import com.app.cqrs.shared.domain.events.orders.OrderCreatedEvent;

public interface IOrderRepository {

    Order createOrder(OrderCreatedEvent event);
    boolean updateOrderStatus(String orderId, OrderStatus orderStatus);
    Optional<Order> findOrderById(String orderId);

}