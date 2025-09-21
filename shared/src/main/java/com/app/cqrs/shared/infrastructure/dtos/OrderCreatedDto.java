package com.app.cqrs.shared.infrastructure.dtos;

import com.app.cqrs.shared.domain.orders.OrderStatus;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderCreatedDto {

    private String orderId;
    private String productId;
    private String userId;
    private int quantity;
    private String addressId;
    private OrderStatus orderStatus;

}
