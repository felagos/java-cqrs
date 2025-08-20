package com.app.cqrs.productcommand.domain.ports;

import com.app.cqrs.productcommand.domain.events.ProductCreatedEvent;

public interface IProductRepository {
    void saveProduct(ProductCreatedEvent product);
    boolean existsProductById(String productId);
}
