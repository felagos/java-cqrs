package com.app.cqrs.command.infrastructure.mappers;

import java.util.UUID;

import org.springframework.stereotype.Component;

import com.app.cqrs.command.domain.OrderStatus;
import com.app.cqrs.command.domain.commands.CreateOrderCommand;
import com.app.cqrs.command.infrastructure.dtos.OrderCreateDto;

@Component
public class OrderMapper {

    public CreateOrderCommand toDomain(OrderCreateDto dto) {
        return new CreateOrderCommand(
            UUID.randomUUID().toString(),
            UUID.randomUUID().toString(),
            dto.getProductId(),
            dto.getQuantity(),
            dto.getAddressId(),
            OrderStatus.CREATED
        );
    }

}
