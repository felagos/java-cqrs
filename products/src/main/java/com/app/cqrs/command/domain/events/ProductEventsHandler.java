package com.app.cqrs.command.domain.events;

import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.messaging.interceptors.ExceptionHandler;
import org.springframework.stereotype.Component;
import java.util.logging.Logger;
import com.app.cqrs.command.domain.ports.IProductCommandRepository;
import com.app.cqrs.shared.constants.ProcessGroups;

@Component
@ProcessingGroup(ProcessGroups.PRODUCT_GROUP)
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
    public void on(ProductCreatedEvent productCreatedEvent) throws Exception {
        LOGGER.info("Handling ProductCreatedEvent for product: " + productCreatedEvent.getProductId());
        productRepository.saveProduct(productCreatedEvent);

        throw new Exception("some error");
    }

}
