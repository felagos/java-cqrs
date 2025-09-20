package com.app.cqrs.command.domain.events.products;

import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.messaging.interceptors.ExceptionHandler;
import org.springframework.stereotype.Component;
import java.util.logging.Logger;
import com.app.cqrs.command.domain.ports.products.IProductCommandRepository;
import com.app.cqrs.shared.constants.ProcessGroups;

@Component
@ProcessingGroup(ProcessGroups.ORDER_GROUP)
public class ProductEventsHandler {

    private final Logger LOGGER = Logger.getLogger(ProductEventsHandler.class.getName());
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
            LOGGER.severe(
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
            LOGGER.severe("Failed to increment product quantity for product ID: " + event.getProductId() +
                    ". Product not found.");
        }
    }

}
