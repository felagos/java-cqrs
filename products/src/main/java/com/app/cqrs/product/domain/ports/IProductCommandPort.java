package com.app.cqrs.product.domain.ports;

import com.app.cqrs.product.domain.commands.CreateProductCommand;

public interface IProductCommandPort {
    <R> R createProduct(CreateProductCommand product);
}
