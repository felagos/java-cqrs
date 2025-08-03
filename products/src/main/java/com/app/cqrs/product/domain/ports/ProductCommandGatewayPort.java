package com.app.cqrs.product.domain.ports;

import com.app.cqrs.product.domain.Product;

public interface ProductCommandGatewayPort {
    <R> R createProduct(Product product);
}
