package com.app.cqrs.query.infrastructure.repositories;

import java.util.List;

import org.springframework.stereotype.Component;

import com.app.cqrs.query.domain.Product;
import com.app.cqrs.query.domain.ports.IProductQueryRepository;
import com.app.cqrs.query.infrastructure.mappers.ProductMapper;

@Component
public class ProductQueryRepository implements IProductQueryRepository {

    private ProductQueryJpa repository;
    private ProductMapper mapper;

    public ProductQueryRepository(ProductQueryJpa repository, ProductMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public List<Product> findAll() {
        var response = this.repository.findAll();
        return this.mapper.toDomainList(response);
    }

}
