package com.app.cqrs.command.domain.events.products;

import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventhandling.ResetHandler;
import org.axonframework.messaging.interceptors.ExceptionHandler;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.app.cqrs.command.domain.ports.products.IProductCommandRepository;
import com.app.cqrs.shared.constants.ProcessGroups;
import com.app.cqrs.shared.domain.events.products.ProductReservedEvent;

@Component
@ProcessingGroup(ProcessGroups.ORDER_GROUP)
public class ProductEventsHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductEventsHandler.class);
    private final IProductCommandRepository productRepository;

    public ProductEventsHandler(IProductCommandRepository productRepository) {
        this.productRepository = productRepository;
    }

    @ExceptionHandler(resultType = RuntimeException.class)
    public void handleException(RuntimeException exception) {
        throw exception;
    }

    @EventHandler
    public void handleProductCreatedEvent(ProductCreatedEvent productCreatedEvent) {
        LOGGER.info("Handling ProductCreatedEvent for product: " + productCreatedEvent.getProductId());
        productRepository.saveProduct(productCreatedEvent);
    }

    @EventHandler
    public void handleProductReservedEvent(ProductReservedEvent productReservedEvent) {
        LOGGER.info("Handling ProductReservedEvent for product: " + productReservedEvent.getProductId());

        boolean success = this.productRepository.decrementQuantityProduct(
                productReservedEvent.getProductId(),
                productReservedEvent.getQuantity());

        if (success) {
            LOGGER.info("Successfully decremented product quantity by " + productReservedEvent.getQuantity() +
                    " for product ID: " + productReservedEvent.getProductId());
        } else {
            LOGGER.error(
                    "Failed to decrement product quantity for product ID: " + productReservedEvent.getProductId() +
                            ". Product not found or insufficient quantity.");
        }
    }

    @EventHandler
    public void handleCancelProductReservationEvent(CancelProductReservationEvent event) {
        LOGGER.info("Handling CancelProductReservationEvent for product: " + event.getProductId());

        boolean success = this.productRepository.incrementQuantityProduct(
                event.getProductId(),
                event.getQuantity());

        if (success) {
            LOGGER.info("Successfully incremented product quantity by " + event.getQuantity() +
                    " for product ID: " + event.getProductId());
        } else {
            LOGGER.error("Failed to increment product quantity for product ID: " + event.getProductId() +
                    ". Product not found.");
        }
    }

    @ResetHandler
    public void reset() {
        this.productRepository.deleteAllProducts();
    }

}
