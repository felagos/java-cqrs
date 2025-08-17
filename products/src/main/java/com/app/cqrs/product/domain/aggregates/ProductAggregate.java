package com.app.cqrs.product.domain.aggregates;

import java.math.BigDecimal;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import com.app.cqrs.product.domain.commands.CreateProductCommand;
import com.app.cqrs.product.domain.events.ProductCreatedEvent;
import com.app.cqrs.product.domain.exceptions.InvalidProductException;

@Aggregate
public class ProductAggregate {

    @AggregateIdentifier
    private String productId;
    private String title;
    private BigDecimal price;
    private Integer quantity;

    public ProductAggregate() {
    }

    @CommandHandler
    public ProductAggregate(CreateProductCommand command) {
        if (command.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidProductException("Invalid product price");
        }

        var productCreatedEvent = new ProductCreatedEvent(
            command.getProductId(),
            command.getTitle(),
            command.getPrice(),
            command.getQuantity()
        );

        System.out.println("Product created: " + productCreatedEvent.getProductId());

        AggregateLifecycle.apply(productCreatedEvent);
    }

    @EventSourcingHandler
    public void on(ProductCreatedEvent event) {
        this.productId = event.getProductId();
        this.title = event.getTitle();
        this.price = event.getPrice();
        this.quantity = event.getQuantity();

        System.out.println("Product aggregate state restored: " + event.getProductId());
    }

}
