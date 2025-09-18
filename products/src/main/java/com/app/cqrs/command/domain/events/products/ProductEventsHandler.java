package com.app.cqrs.command.domain.events.products;

import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.messaging.interceptors.ExceptionHandler;
import org.springframework.stereotype.Component;
import java.util.logging.Logger;
import com.app.cqrs.command.domain.ports.products.IProductCommandRepository;
import com.app.cqrs.shared.constants.ProcessGroups;
import com.app.cqrs.shared.domain.Product;

@Component
@ProcessingGroup(ProcessGroups.PRODUCT_GROUP)
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

        var productOptional = productRepository.findProductById(productReservedEvent.getProductId());

        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            var productId = product.getId();
            var newQuantity = product.getQuantity() - productReservedEvent.getQuantity();

            this.productRepository.updateQuantityProduct(productId, newQuantity);

            LOGGER.info("Updated product quantity: " + newQuantity + " for product ID: " + productId);
        }

    }

}
