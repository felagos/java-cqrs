package com.app.cqrs.command.domain.events;

import java.math.BigDecimal;

public class ProductCreatedEvent {
    private final String productId;
    private final String title;
    private final BigDecimal price;
    private final Integer quantity;

    public ProductCreatedEvent(String productId, String title, BigDecimal price, Integer quantity) {
        this.productId = productId;
        this.title = title;
        this.price = price;
        this.quantity = quantity;
    }

    public String getProductId() { return productId; }
    public String getTitle() { return title; }
    public BigDecimal getPrice() { return price; }
    public Integer getQuantity() { return quantity; }
}
