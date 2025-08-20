package com.app.cqrs.productCommand.domain.events;

import java.math.BigDecimal;

/**
 * Event indicating that a new product has been successfully created.
 * 
 * <p>This event is published when a product is created through the command handling
 * process and contains all the essential information about the newly created product.
 */
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

    public String getProductId() {
        return productId;
    }

    public String getTitle() {
        return title;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Integer getQuantity() {
        return quantity;
    }
}
