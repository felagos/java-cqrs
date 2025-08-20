package com.app.cqrs.productCommand.domain.ports;

import com.app.cqrs.productCommand.domain.events.ProductCreatedEvent;

public interface IProductRepository {
    void saveProduct(ProductCreatedEvent product);
    boolean existsProductById(String productId);
}
