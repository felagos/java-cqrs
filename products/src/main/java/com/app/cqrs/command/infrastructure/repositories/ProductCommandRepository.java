package com.app.cqrs.command.infrastructure.repositories;

import java.util.Optional;
import java.util.logging.Logger;

import org.springframework.stereotype.Repository;

import com.app.cqrs.command.domain.events.ProductCreatedEvent;
import com.app.cqrs.command.domain.ports.IProductCommandRepository;
import com.app.cqrs.command.infrastructure.mappers.ProductMapper;
import com.app.cqrs.shared.infrastructure.entities.ProductEntity;

@Repository
public class ProductCommandRepository implements IProductCommandRepository {

    private static final Logger LOGGER = Logger.getLogger(ProductCommandRepository.class.getName());

    private final ProductRepositoryJpa productRepositoryJpa;
    private final ProductMapper productMapper;

    public ProductCommandRepository(ProductRepositoryJpa productRepositoryJpa, ProductMapper productMapper) {
        this.productRepositoryJpa = productRepositoryJpa;
        this.productMapper = productMapper;
    }

    @Override
    public void saveProduct(ProductCreatedEvent product) {
        var entity = productMapper.toEntity(product);
        LOGGER.info("Saving product: " + entity);
        productRepositoryJpa.save(entity);
    }

    @Override
    public boolean existsProductByTitle(String productId) {
        var product = this.productRepositoryJpa.findByTitle(productId);
        if (product.isPresent()) {
            LOGGER.info("Product found: " + product.get());
        }
        return product.isPresent();
    }

    public Optional<ProductEntity> findByProductIdOrTitle(String id, String title) {
        return this.productRepositoryJpa.findByIdOrTitle(id, title);
    }

}
