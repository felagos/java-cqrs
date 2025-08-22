package com.app.cqrs.command.domain.ports;

import com.app.cqrs.command.domain.commands.CreateProductCommand;

public interface IProductCommandPort {
    <R> R createProduct(CreateProductCommand product);
}
