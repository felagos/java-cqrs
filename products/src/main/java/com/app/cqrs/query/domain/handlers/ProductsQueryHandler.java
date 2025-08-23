package com.app.cqrs.query.domain.handlers;

import java.util.List;

import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;
import com.app.cqrs.query.domain.Product;
import com.app.cqrs.query.domain.ports.IProductQueryRepository;

@Component
public class ProductsQueryHandler {

    private final IProductQueryRepository repository;

    public ProductsQueryHandler(IProductQueryRepository repository) {
        this.repository = repository;
    }

    @QueryHandler
    public List<Product> findAllProducts() {        
        return this.repository.findAll();
    }

}
