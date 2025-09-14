package com.app.cqrs.command.domain.ports.products;

import com.app.cqrs.command.domain.commands.CreateProductCommand;
import com.app.cqrs.shared.domain.Product;

public interface IProductCommandPort {
    Product createProduct(CreateProductCommand product);
}
