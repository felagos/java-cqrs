package com.app.cqrs.shared.domain.events.orders;

import com.app.cqrs.shared.domain.orders.OrderStatus;
import lombok.Data;

@Data
public class OrderApprovedEvent {

    private String orderId;
    private OrderStatus orderStatus;

    public OrderApprovedEvent(String orderId) {
        this.orderId = orderId;
        this.orderStatus = OrderStatus.APPROVED;
    }

}