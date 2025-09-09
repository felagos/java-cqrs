package com.app.cqrs.command.infrastructure.repositories.order;

import org.springframework.stereotype.Repository;

import com.app.cqrs.command.domain.Order;
import com.app.cqrs.command.domain.events.OrderCreatedEvent;
import com.app.cqrs.command.domain.ports.IOrderRepository;
import com.app.cqrs.command.infrastructure.mappers.OrderMapper;

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

        return this.orderMapper.toDomain(orderCreated);
    }

}
