package com.app.cqrs.command.infrastructure.repositories.order;

import org.springframework.stereotype.Repository;

import com.app.cqrs.shared.domain.orders.Order;
import com.app.cqrs.shared.domain.orders.OrderStatus;
import com.app.cqrs.shared.domain.events.orders.OrderCreatedEvent;
import com.app.cqrs.shared.domain.ports.orders.IOrderRepository;

@Repository
public class OrderRepository implements IOrderRepository {

    private final com.app.cqrs.shared.infrastructure.repositories.orders.OrderRepository sharedOrderRepository;

    public OrderRepository(com.app.cqrs.shared.infrastructure.repositories.orders.OrderRepository sharedOrderRepository) {
        this.sharedOrderRepository = sharedOrderRepository;
    }

    @Override
    public Order createOrder(OrderCreatedEvent event) {
        return sharedOrderRepository.createOrder(event);
    }

    @Override
    public boolean updateOrderStatus(String orderId, OrderStatus orderStatus) {
        return sharedOrderRepository.updateOrderStatus(orderId, orderStatus);
    }

    @Override
    public Order findOrderById(String orderId) {
        return sharedOrderRepository.findOrderById(orderId);
    }

}
