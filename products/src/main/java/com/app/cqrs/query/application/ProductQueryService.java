package com.app.cqrs.query.application;

import java.util.List;
import org.springframework.stereotype.Service;
import com.app.cqrs.query.domain.Product;
import com.app.cqrs.query.domain.ProductFilter;
import com.app.cqrs.query.domain.ports.IProductQueryGateway;

@Service
public class ProductQueryService {

    private final IProductQueryGateway productQueryGateway;

    public ProductQueryService(IProductQueryGateway productQueryGateway) {
        this.productQueryGateway = productQueryGateway;
    }

    public List<Product> getAllProducts(ProductFilter filter) {
        if (filter.isEmpty()) return this.productQueryGateway.findAll();
        return this.productQueryGateway.findByFilter(filter);
    }

}
