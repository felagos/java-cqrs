package com.app.cqrs.command.domain.ports;

import com.app.cqrs.command.domain.commands.CreateProductCommand;
import com.app.cqrs.query.domain.Product;

public interface IProductCommandPort {
    Product createProduct(CreateProductCommand product);
}
