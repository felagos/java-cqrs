package com.app.cqrs.product.infrastructure.repository;

import org.springframework.stereotype.Repository;

import com.app.cqrs.product.domain.events.ProductCreatedEvent;
import com.app.cqrs.product.domain.ports.IProductRepository;
import com.app.cqrs.product.infrastructure.mappers.ProductMapper;

@Repository
public class ProductRepository implements IProductRepository {

    private final ProductRepositoryJpa productRepositoryJpa;
    private final ProductMapper productMapper;

    public ProductRepository(ProductRepositoryJpa productRepositoryJpa, ProductMapper productMapper) {
        this.productRepositoryJpa = productRepositoryJpa;
        this.productMapper = productMapper;
    }

    @Override
    public void saveProduct(ProductCreatedEvent product) {
        var entity = productMapper.toEntity(product);
        productRepositoryJpa.save(entity);
    }

    @Override
    public boolean existsProductById(String productId) {
        var product = this.productRepositoryJpa.findById(productId);
        return product.isPresent();
    }

    public void findByProductIdOrTitle(String id, String title) {
        this.productRepositoryJpa.findByIdOrTitle(id, title);
    }

}
