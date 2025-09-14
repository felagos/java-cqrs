package com.app.cqrs.command.domain.events.products;

public class ProductReservedEvent {

    private String productId;
    private int quantity;
    private String orderId;
    private String userId;

    public ProductReservedEvent() {
    }

    public ProductReservedEvent(String productId, int quantity, String orderId, String userId) {
        this.productId = productId;
        this.quantity = quantity;
        this.orderId = orderId;
        this.userId = userId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public void setUserId(String userId) {
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
