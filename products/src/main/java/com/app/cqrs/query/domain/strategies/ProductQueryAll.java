package com.app.cqrs.query.domain.strategies;

import java.util.List;
import com.app.cqrs.query.domain.Product;
import com.app.cqrs.query.domain.ports.IProductQueryGateway;

public class ProductQueryAll implements ProductQueryStrategy {

    private final IProductQueryGateway productQueryGateway;

    public ProductQueryAll(IProductQueryGateway productQueryGateway) {
        this.productQueryGateway = productQueryGateway;
    }

    @Override
    public List<Product> getProducts() {
        return this.productQueryGateway.findAll();
    }

}
