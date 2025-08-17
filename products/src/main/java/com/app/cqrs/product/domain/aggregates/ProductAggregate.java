package com.app.cqrs.product.domain.aggregates;

import java.math.BigDecimal;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import com.app.cqrs.product.domain.commands.CreateProductCommand;
import com.app.cqrs.product.domain.events.ProductCreatedEvent;

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
        if (command.price().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Invalid product price");
        }

        var productCreatedEvent = new ProductCreatedEvent(
            command.productId(),
            command.title(),
            command.price(),
            command.quantity()
        );

        AggregateLifecycle.apply(productCreatedEvent);
    }

    @EventSourcingHandler
    public void on(ProductCreatedEvent event) {
        this.productId = event.productId();
        this.title = event.title();
        this.price = event.price();
        this.quantity = event.quantity();
    }

}
