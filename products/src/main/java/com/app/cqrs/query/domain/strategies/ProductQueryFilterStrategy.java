package com.app.cqrs.query.domain.strategies;

import java.util.List;

import com.app.cqrs.query.domain.ProductFilter;
import com.app.cqrs.query.domain.ports.IProductQueryGateway;
import com.app.cqrs.shared.domain.Product;

public class ProductQueryFilterStrategy implements ProductQueryStrategy {

    private final IProductQueryGateway productQueryGateway;
    private final ProductFilter filter;

    public ProductQueryFilterStrategy(IProductQueryGateway productQueryGateway, ProductFilter filter) {
        this.productQueryGateway = productQueryGateway;
        this.filter = filter;
    }

    @Override
    public List<Product> getProducts() {
        return this.productQueryGateway.findByFilter(this.filter);
    }

}
