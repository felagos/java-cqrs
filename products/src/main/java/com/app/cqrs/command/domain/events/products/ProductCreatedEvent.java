package com.app.cqrs.command.domain.events.products;

import java.math.BigDecimal;
import lombok.Value;

/**
 * Event indicating that a new product has been successfully created.
 * 
 * <p>This event is published when a product is created through the command handling
 * process and contains all the essential information about the newly created product.
 */
@Value
public class ProductCreatedEvent {
    
    String productId;
    String title;
    BigDecimal price;
    Integer quantity;

}
