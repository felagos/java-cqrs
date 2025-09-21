package com.app.cqrs.shared.infrastructure.mappers;

import java.util.UUID;

import org.springframework.stereotype.Component;

import com.app.cqrs.shared.domain.orders.Order;
import com.app.cqrs.shared.domain.orders.OrderStatus;
import com.app.cqrs.shared.domain.commands.orders.CreateOrderCommand;
import com.app.cqrs.shared.domain.commands.orders.RejectOrderCommand;
import com.app.cqrs.shared.domain.events.orders.OrderCreatedEvent;
import com.app.cqrs.shared.infrastructure.entities.OrderEntity;
import com.app.cqrs.shared.domain.commands.ReserveProductCommand;
import com.app.cqrs.shared.infrastructure.dtos.OrderCreateDto;
import com.app.cqrs.shared.domain.commands.CancelProductReservationCommand;
import com.app.cqrs.shared.domain.events.products.ProductReservedEvent;

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

    public CancelProductReservationCommand toCancelReservation(ProductReservedEvent event, String reason) {
        return CancelProductReservationCommand.builder()
                .orderId(event.getOrderId())
                .productId(event.getProductId())
                .quantity(event.getQuantity())
                .userId(event.getUserId())
                .reason(reason)
                .build();
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

    public ReserveProductCommand toReservationCommand(OrderCreatedEvent event) {
        return new ReserveProductCommand(
                event.getProductId(),
                event.getQuantity(),
                event.getOrderId(),
                event.getUserId());
    }

    public RejectOrderCommand toRejectOrderCommand(String orderId, String reason) {
        return RejectOrderCommand.builder()
                .orderId(orderId)
                .reason(reason)
                .build();
    }
}