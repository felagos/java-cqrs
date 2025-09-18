package com.app.cqrs.command.infrastructure.repositories.order;

import org.springframework.stereotype.Repository;

import com.app.cqrs.command.domain.Order;
import com.app.cqrs.command.domain.OrderStatus;
import com.app.cqrs.command.domain.events.orders.OrderCreatedEvent;
import com.app.cqrs.command.domain.ports.orders.IOrderRepository;
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

    @Override
    public boolean updateOrderStatus(String orderId, OrderStatus orderStatus) {
        int updatedRows = ordersRepositoryJpa.updateOrderStatusById(orderId, orderStatus);
        return updatedRows > 0;
    }

}
