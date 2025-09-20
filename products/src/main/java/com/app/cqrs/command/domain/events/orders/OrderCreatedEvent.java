package com.app.cqrs.command.domain.events.orders;

import com.app.cqrs.command.domain.OrderStatus;
import lombok.Data;

@Data
public class OrderCreatedEvent {
    private String orderId;
    private String productId;
    private String userId;
    private int quantity;
    private String addressId;
    private OrderStatus orderStatus;
}
