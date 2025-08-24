package com.app.cqrs.query.infrastructure.repositories;

import java.util.List;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import com.app.cqrs.query.domain.Product;
import com.app.cqrs.query.domain.ports.IProductQueryRepository;
import com.app.cqrs.query.infrastructure.mappers.ProductMapper;
import com.app.cqrs.query.domain.ProductFilter;
import org.springframework.data.mongodb.core.query.Query;

@Component
public class ProductQueryRepository implements IProductQueryRepository {

    private ProductQueryJpa repository;
    private ProductMapper mapper;
    private MongoTemplate mongoTemplate;

    public ProductQueryRepository(ProductQueryJpa repository, ProductMapper mapper, MongoTemplate mongoTemplate) {
        this.repository = repository;
        this.mapper = mapper;
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public List<Product> findAll() {
        var response = this.repository.findAll();
        return this.mapper.toDomainList(response);
    }

    @Override
    public List<Product> findByFilter(ProductFilter filter) {
        Query query = ProductSpecification.buildQuery(filter);
        var response = this.mongoTemplate.find(query, com.app.cqrs.query.infrastructure.entities.ProductEntity.class);
        return this.mapper.toDomainList(response);
    }

}
