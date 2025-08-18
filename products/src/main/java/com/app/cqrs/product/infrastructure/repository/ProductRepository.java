package com.app.cqrs.product.infrastructure.repository;

import org.springframework.stereotype.Repository;

import com.app.cqrs.product.domain.ports.IProductRepository;

@Repository
public class ProductRepository implements IProductRepository {

    private final ProductRepositoryJpa productRepositoryJpa;

    public ProductRepository(ProductRepositoryJpa productRepositoryJpa) {
        this.productRepositoryJpa = productRepositoryJpa;
    }

    @Override
    public void saveProduct() {}

}
