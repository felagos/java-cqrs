package com.app.cqrs.query.infrastructure.repositories;

import java.util.List;

import org.springframework.stereotype.Component;

import com.app.cqrs.query.domain.Product;
import com.app.cqrs.query.domain.ports.IProductQueryRepository;
import com.app.cqrs.query.infrastructure.mappers.ProductMapper;
import com.app.cqrs.query.domain.ProductFilter;
import java.util.stream.Collectors;
import java.math.BigDecimal;

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
        var all = this.findAll();

        return all.stream()
            .filter(p -> {
                // title contains (case-insensitive)
                if (filter.getTitle() != null && !filter.getTitle().isBlank()) {
                    if (p.getTitle() == null || !p.getTitle().toLowerCase().contains(filter.getTitle().toLowerCase())) {
                        return false;
                    }
                }

                BigDecimal price = p.getPrice();
                if (filter.getMinPrice() != null) {
                    if (price == null || price.compareTo(filter.getMinPrice()) < 0) {
                        return false;
                    }
                }
                if (filter.getMaxPrice() != null) {
                    if (price == null || price.compareTo(filter.getMaxPrice()) > 0) {
                        return false;
                    }
                }

                return true;
            })
            .collect(Collectors.toList());
    }

}
