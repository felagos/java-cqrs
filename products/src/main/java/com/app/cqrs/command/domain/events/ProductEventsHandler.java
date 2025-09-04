package com.app.cqrs.command.domain.events;

import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.messaging.interceptors.ExceptionHandler;
import org.springframework.stereotype.Component;
import java.util.logging.Logger;
import com.app.cqrs.command.domain.ports.IProductCommandRepository;

@Component
@ProcessingGroup("product-group")
public class ProductEventsHandler {

    private static final Logger LOGGER = Logger.getLogger(ProductEventsHandler.class.getName());
    private final IProductCommandRepository productRepository;

    public ProductEventsHandler(IProductCommandRepository productRepository) {
        this.productRepository = productRepository;
    }

    @ExceptionHandler(resultType = Exception.class)
    public void handleException(Exception exception) throws Exception {
        throw exception;
    }

    @EventHandler
    public void on(ProductCreatedEvent productCreatedEvent) {
        LOGGER.info("Handling ProductCreatedEvent for product: " + productCreatedEvent.getProductId());
        productRepository.saveProduct(productCreatedEvent);
    }

}
