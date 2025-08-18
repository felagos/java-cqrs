package com.app.cqrs.product.domain.ports;

import com.app.cqrs.product.domain.events.ProductCreatedEvent;

public interface IProductRepository {
    void saveProduct(ProductCreatedEvent product);
    boolean existsProductById(String productId);
}
