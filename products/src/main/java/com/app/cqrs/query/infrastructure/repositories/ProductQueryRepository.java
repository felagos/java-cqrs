package com.app.cqrs.query.infrastructure.repositories;

import java.util.List;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import com.app.cqrs.query.domain.Product;
import com.app.cqrs.query.domain.ports.IProductQueryRepository;
import com.app.cqrs.query.infrastructure.mappers.ProductMapper;
import com.app.cqrs.query.domain.ProductFilter;

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

    @Override
    public List<Product> findByFilter(ProductFilter filter) {
        var filterSpec = Specification.allOf(
                ProductSpecification.whereLikeTitle(filter),
                ProductSpecification.whereMinPrice(filter),
                ProductSpecification.whereMaxPrice(filter));

        var response = this.repository.findAll(filterSpec);
        return this.mapper.toDomainList(response);
    }

}
