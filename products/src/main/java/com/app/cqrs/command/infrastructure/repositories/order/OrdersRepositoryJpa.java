package com.app.cqrs.command.infrastructure.repositories.order;

import org.springframework.data.repository.CrudRepository;

import com.app.cqrs.command.infrastructure.entities.OrderEntity;

public interface OrdersRepositoryJpa extends CrudRepository<OrderEntity, String> {

}
