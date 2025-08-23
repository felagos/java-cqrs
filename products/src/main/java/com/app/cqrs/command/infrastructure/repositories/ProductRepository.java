package com.app.cqrs.command.infrastructure.repositories;

import java.util.Optional;

import com.app.cqrs.shared.infrastructure.entities.ProductEntity;
import org.springframework.stereotype.Repository;

import com.app.cqrs.command.domain.events.ProductCreatedEvent;
import com.app.cqrs.command.domain.ports.IProductRepository;
import com.app.cqrs.command.infrastructure.mappers.ProductMapper;
import com.app.cqrs.shared.infrastructure.repository.ProductRepositoryJpa;

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

    public Optional<ProductEntity> findByProductIdOrTitle(String id, String title) {
        return this.productRepositoryJpa.findByIdOrTitle(id, title);
    }

}
