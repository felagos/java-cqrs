package com.app.cqrs.query.domain.handlers.product;

import java.util.List;

import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

import com.app.cqrs.query.domain.ports.IProductQueryRepository;
import com.app.cqrs.query.domain.queries.product.FilterProductQuery;
import com.app.cqrs.shared.domain.Product;

@Component
public class FilterProductQueryHandler {

    private final IProductQueryRepository repository;

    public FilterProductQueryHandler(IProductQueryRepository repository) {
        this.repository = repository;
    }

    @QueryHandler
    public List<Product> findProductsByFilter(FilterProductQuery query) {
        return this.repository.findByFilter(query.getFilter());
    }

}
