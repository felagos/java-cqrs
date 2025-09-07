package com.app.cqrs.command.infrastructure.mappers;

import java.util.UUID;

import org.springframework.stereotype.Component;

import com.app.cqrs.command.domain.Order;
import com.app.cqrs.command.domain.OrderStatus;
import com.app.cqrs.command.domain.commands.CreateOrderCommand;
import com.app.cqrs.command.domain.events.OrderCreatedEvent;
import com.app.cqrs.command.infrastructure.dtos.OrderCreateDto;
import com.app.cqrs.command.infrastructure.entities.OrderEntity;

@Component
public class OrderMapper {

    public CreateOrderCommand toDomain(OrderCreateDto dto) {
        return new CreateOrderCommand(
                UUID.randomUUID().toString(),
                UUID.randomUUID().toString(),
                dto.getProductId(),
                dto.getQuantity(),
                dto.getAddressId(),
                OrderStatus.CREATED);
    }

    public OrderEntity toEntity(OrderCreatedEvent command) {
        var entity = new OrderEntity();

        entity.setOrderId(command.getOrderId());
        entity.setUserId(command.getUserId());
        entity.setProductId(command.getProductId());
        entity.setQuantity(command.getQuantity());
        entity.setAddressId(command.getAddressId());
        entity.setOrderStatus(OrderStatus.CREATED);

        return entity;
    }

    public Order toDomain(OrderEntity entity) {
        return new Order(
                entity.getOrderId(),
                entity.getProductId(),
                entity.getUserId(),
                entity.getQuantity(),
                entity.getAddressId(),
                entity.getOrderStatus());
    }

}
