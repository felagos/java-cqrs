package com.app.cqrs.query.application;

import java.util.List;
import org.springframework.stereotype.Service;

import com.app.cqrs.query.domain.ProductFilter;
import com.app.cqrs.query.domain.ports.IProductQueryGateway;
import com.app.cqrs.query.domain.strategies.ProductQueryFactory;
import com.app.cqrs.shared.domain.Product;

@Service
public class ProductQueryService {

    private final ProductQueryFactory productQueryFactory;

    public ProductQueryService(IProductQueryGateway productQueryGateway) {
        this.productQueryFactory = new ProductQueryFactory(productQueryGateway);
    }

    public List<Product> getAllProducts(ProductFilter filter) {
        return this.productQueryFactory.create(filter).getProducts();
    }

}
