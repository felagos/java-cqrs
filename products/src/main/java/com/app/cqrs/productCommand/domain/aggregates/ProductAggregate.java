package com.app.cqrs.productCommand.domain.aggregates;

import java.math.BigDecimal;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import com.app.cqrs.productCommand.domain.commands.CreateProductCommand;
import com.app.cqrs.productCommand.domain.events.ProductCreatedEvent;
import com.app.cqrs.productCommand.domain.exceptions.InvalidProductException;
import com.app.cqrs.shared.domain.aggregates.BaseAggregate;

@Aggregate
public class ProductAggregate extends BaseAggregate<ProductCreatedEvent> {

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
                command.getQuantity());

        System.out.println("Product created: " + productCreatedEvent.getProductId());

        AggregateLifecycle.apply(productCreatedEvent);
    }

    @EventSourcingHandler
    public void on(ProductCreatedEvent event) {
        this.id = event.getProductId();
        this.title = event.getTitle();
        this.price = event.getPrice();
        this.quantity = event.getQuantity();

        System.out.println("Product aggregate state restored: " + event.getProductId());

    }

}
