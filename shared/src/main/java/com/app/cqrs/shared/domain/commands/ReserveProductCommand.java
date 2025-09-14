package com.app.cqrs.shared.domain.commands;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

public class ReserveProductCommand {

    @TargetAggregateIdentifier
    private String productId;
    private int quantity;
    private String orderId;
    private String userId;

    public ReserveProductCommand() {
    }

    public ReserveProductCommand(String productId, int quantity, String orderId, String userId) {
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

    @Override
    public String toString() {
        return "ReserveProductCommand [productId=" + productId + ", quantity=" + quantity + ", orderId=" + orderId
                + ", userId=" + userId + "]";
    }

}
