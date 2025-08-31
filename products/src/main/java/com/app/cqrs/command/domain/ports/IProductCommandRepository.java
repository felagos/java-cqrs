package com.app.cqrs.command.domain.ports;

import com.app.cqrs.command.domain.events.ProductCreatedEvent;

public interface IProductCommandRepository {
    void saveProduct(ProductCreatedEvent product);

    boolean existsProductByTitle(String name);
}
