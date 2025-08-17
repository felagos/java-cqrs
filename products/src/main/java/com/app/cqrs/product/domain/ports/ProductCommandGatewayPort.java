package com.app.cqrs.product.domain.ports;

import com.app.cqrs.product.domain.commands.CreateProductCommand;

public interface ProductCommandGatewayPort {
    <R> R createProduct(CreateProductCommand product);
}
