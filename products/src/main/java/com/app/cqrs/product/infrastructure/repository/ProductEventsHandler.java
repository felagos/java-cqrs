package com.app.cqrs.product.infrastructure.repository;

import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

import com.app.cqrs.product.domain.events.ProductCreatedEvent;
import com.app.cqrs.product.infrastructure.mappers.ProductMapper;

@Component
public class ProductEventsHandler {

    private final ProductRepositoryJpa productRepositoryJpa;
    private final ProductMapper productMapper;

    public ProductEventsHandler(ProductRepositoryJpa productRepositoryJpa, ProductMapper productMapper) {
        this.productRepositoryJpa = productRepositoryJpa;
        this.productMapper = productMapper;
    }

    @EventHandler
    public void on(ProductCreatedEvent productCreatedEvent) {
        var productEntity = productMapper.toEntity(productCreatedEvent);
        productRepositoryJpa.save(productEntity);
    }

}
