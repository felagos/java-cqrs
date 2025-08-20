package com.app.cqrs.productcommand.domain.ports;

import com.app.cqrs.productcommand.domain.commands.CreateProductCommand;

public interface IProductCommandPort {
    <R> R createProduct(CreateProductCommand product);
}
