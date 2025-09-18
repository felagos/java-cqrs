package com.app.cqrs.command.domain.events.orders;

import com.app.cqrs.command.domain.OrderStatus;

public class OrderApprovedEvent {

    private String orderId;
    private OrderStatus orderStatus;

    public OrderApprovedEvent(String orderId) {
        this.orderId = orderId;
        this.orderStatus = OrderStatus.APPROVED;
    }

    public String getOrderId() {
        return orderId;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

}
