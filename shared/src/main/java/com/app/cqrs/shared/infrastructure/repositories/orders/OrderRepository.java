package com.app.cqrs.shared.infrastructure.repositories.orders;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.app.cqrs.shared.domain.orders.Order;
import com.app.cqrs.shared.domain.orders.OrderStatus;
import com.app.cqrs.shared.domain.events.orders.OrderCreatedEvent;
import com.app.cqrs.shared.domain.ports.orders.IOrderRepository;
import com.app.cqrs.shared.infrastructure.mappers.OrderMapper;

@Repository
public class OrderRepository implements IOrderRepository {

    private final OrdersRepositoryJpa ordersRepositoryJpa;
    private final OrderMapper orderMapper;

    public OrderRepository(OrdersRepositoryJpa ordersRepositoryJpa, OrderMapper orderMapper) {
        this.ordersRepositoryJpa = ordersRepositoryJpa;
        this.orderMapper = orderMapper;
    }

    @Override
    public Order createOrder(OrderCreatedEvent event) {
        var orderEntity = orderMapper.toEntity(event);
        var orderCreated = ordersRepositoryJpa.save(orderEntity);
        return orderMapper.toDomain(orderCreated);
    }

    @Override
    public boolean updateOrderStatus(String orderId, OrderStatus orderStatus) {
        int updatedRows = ordersRepositoryJpa.updateOrderStatusById(orderId, orderStatus);
        return updatedRows > 0;
    }

    @Override
    public Optional<Order> findOrderById(String orderId) {
        var orderEntity = ordersRepositoryJpa.findById(orderId);
        
        return orderEntity.map(orderMapper::toDomain);
    }
}