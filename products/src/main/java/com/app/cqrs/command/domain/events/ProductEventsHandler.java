package com.app.cqrs.command.domain.events;

import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

import com.app.cqrs.command.domain.ports.IProductCommandRepository;

@Component
public class ProductEventsHandler {

    private final IProductCommandRepository productRepository;

    public ProductEventsHandler(IProductCommandRepository productRepository) {
        this.productRepository = productRepository;
    }

    @EventHandler
    public void on(ProductCreatedEvent productCreatedEvent) {
        productRepository.saveProduct(productCreatedEvent);
    }

}
