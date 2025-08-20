package com.app.cqrs.productCommand.domain.ports;

import com.app.cqrs.productCommand.domain.commands.CreateProductCommand;

public interface IProductCommandPort {
    <R> R createProduct(CreateProductCommand product);
}
