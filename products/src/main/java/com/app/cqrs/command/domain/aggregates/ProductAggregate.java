package com.app.cqrs.command.domain.aggregates;

import java.math.BigDecimal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import com.app.cqrs.command.domain.commands.CancelProductReservationCommand;
import com.app.cqrs.command.domain.commands.CreateProductCommand;
import com.app.cqrs.command.domain.events.products.CancelProductReservationEvent;
import com.app.cqrs.command.domain.events.products.ProductCreatedEvent;
import com.app.cqrs.command.domain.events.products.ProductReservedEvent;
import com.app.cqrs.command.domain.exceptions.InvalidProductException;
import com.app.cqrs.command.domain.exceptions.ReserveProductException;
import com.app.cqrs.shared.domain.BaseAggregate;
import com.app.cqrs.shared.domain.commands.ReserveProductCommand;

@Aggregate
public class ProductAggregate extends BaseAggregate<ProductCreatedEvent> {

    private static final Logger logger = LoggerFactory.getLogger(ProductAggregate.class);

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

        logger.info("Product created: " + productCreatedEvent.getProductId());

        AggregateLifecycle.apply(productCreatedEvent);
    }

    @CommandHandler
    public void handleReservation(ReserveProductCommand command) {
        if (this.quantity < command.getQuantity()) {
            throw new ReserveProductException("Insufficient product quantity");
        }

        var productReservedEvent = new ProductReservedEvent(
                command.getProductId(),
                command.getQuantity(),
                command.getOrderId(),
                command.getUserId());

        logger.info("Product reserved: " + productReservedEvent.getProductId() + " for order "
                + productReservedEvent.getOrderId());

        AggregateLifecycle.apply(productReservedEvent);
    }

    @CommandHandler
    public void handleCancelReservation(CancelProductReservationCommand command) {

        var cancelProductEvent = CancelProductReservationEvent.builder()
                .productId(command.getProductId())
                .quantity(command.getQuantity())
                .orderId(command.getOrderId())
                .userId(command.getUserId())
                .reason(command.getReason())
                .build();

        AggregateLifecycle.apply(cancelProductEvent);
    }

    @EventSourcingHandler
    public void onProductCreatedEvent(ProductCreatedEvent event) {
        this.id = event.getProductId();
        this.title = event.getTitle();
        this.price = event.getPrice();
        this.quantity = event.getQuantity();

        logger.info("Product aggregate state restored: " + event.getProductId());
    }

    @EventSourcingHandler
    public void onProductReservedEvent(ProductReservedEvent event) {
        this.quantity -= event.getQuantity();

        logger.info("Product aggregate state restored: " + event.getProductId());
    }

    @EventSourcingHandler
    public void onCancelProductReservationEvent(CancelProductReservationEvent event) {
        this.quantity += event.getQuantity();

        logger.info("Product reservation cancelled: " + event.getProductId() + " for order " + event.getOrderId());
    }

}
