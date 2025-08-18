package com.app.cqrs.product.domain.events;

import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

import com.app.cqrs.product.domain.ports.IProductRepository;

@Component
public class ProductEventsHandler {

    private final IProductRepository productRepository;

    public ProductEventsHandler(IProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @EventHandler
    public void on(ProductCreatedEvent productCreatedEvent) {
        productRepository.saveProduct(productCreatedEvent);
    }

}
