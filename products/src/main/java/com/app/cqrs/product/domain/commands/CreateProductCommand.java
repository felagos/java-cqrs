package com.app.cqrs.product.domain.commands;

import java.math.BigDecimal;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

public class CreateProductCommand {
    
    @TargetAggregateIdentifier
    private final String productId;
    private final String title;
    private final BigDecimal price;
    private final Integer quantity;

    public CreateProductCommand(String productId, String title, BigDecimal price, Integer quantity) {
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
