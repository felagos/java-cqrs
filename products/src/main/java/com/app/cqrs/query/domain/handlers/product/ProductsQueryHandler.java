package com.app.cqrs.query.domain.handlers.product;

import java.util.List;

import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

import com.app.cqrs.query.domain.ports.IProductQueryRepository;
import com.app.cqrs.query.domain.queries.product.FindProductQuery;
import com.app.cqrs.shared.domain.Product;

@Component
public class ProductsQueryHandler {

    private final IProductQueryRepository repository;

    public ProductsQueryHandler(IProductQueryRepository repository) {
        this.repository = repository;
    }

    @QueryHandler
    public List<Product> findAllProducts(FindProductQuery query) {        
        return this.repository.findAll();
    }

}
