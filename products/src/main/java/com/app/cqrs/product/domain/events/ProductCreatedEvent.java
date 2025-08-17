package com.app.cqrs.product.domain.events;

import java.math.BigDecimal;

/**
 * Event indicating that a new product has been successfully created.
 * 
 * <p>This event is published when a product is created through the command handling
 * process and contains all the essential information about the newly created product.
 * 
 * @param productId the unique identifier of the created product
 * @param title the title/name of the product
 * @param price the price of the product, must be greater than zero
 * @param quantity the initial quantity/stock of the product
 */
public record ProductCreatedEvent(
    String productId,
    String title, 
    BigDecimal price,
    Integer quantity
) {}
