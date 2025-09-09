package com.app.cqrs.shared.domain.commands;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

public class ReservedCommandEvent {

    @TargetAggregateIdentifier
    private final String productId;
    private final int quantity;
    private final String orderId;
    private final String userId;

    public ReservedCommandEvent() {
        this.productId = null;
        this.quantity = 0;
        this.orderId = null;
        this.userId = null;
    }

    public ReservedCommandEvent(String productId, int quantity, String orderId, String userId) {
        this.productId = productId;
        this.quantity = quantity;
        this.orderId = orderId;
        this.userId = userId;
    }

    public String getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getUserId() {
        return userId;
    }


}
