package com.app.cqrs.query.domain.strategies;

import com.app.cqrs.query.domain.ProductFilter;
import com.app.cqrs.query.domain.ports.IProductQueryGateway;

public class ProductQueryFactory {

    private final IProductQueryGateway productQueryGateway;

    public ProductQueryFactory(IProductQueryGateway productQueryGateway) {
        this.productQueryGateway = productQueryGateway;
    }

    public ProductQueryStrategy create(ProductFilter filter) {
        if (filter.isEmpty()) {
            return new ProductQueryAll(productQueryGateway);
        }
        return new ProductQueryFilterStrategy(productQueryGateway, filter);
    }

}
