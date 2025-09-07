package com.app.cqrs.command.infrastructure.repositories;

import org.springframework.stereotype.Repository;

import com.app.cqrs.command.domain.ports.IOrderRepository;

@Repository
public class OrderRepository implements IOrderRepository {

    private final OrdersRepositoryJpa ordersRepositoryJpa;

    public OrderRepository(OrdersRepositoryJpa ordersRepositoryJpa) {
        this.ordersRepositoryJpa = ordersRepositoryJpa;
    }

}
