package com.app.cqrs.command.infrastructure.repositories.product;

import java.util.Optional;
import java.util.logging.Logger;

import org.springframework.stereotype.Repository;

import com.app.cqrs.command.domain.events.products.ProductCreatedEvent;
import com.app.cqrs.command.domain.ports.IProductCommandRepository;
import com.app.cqrs.command.infrastructure.mappers.ProductMapper;
import com.app.cqrs.shared.domain.Product;
import com.app.cqrs.shared.infrastructure.entities.ProductEntity;

@Repository
public class ProductCommandRepository implements IProductCommandRepository {

    private final Logger LOGGER = Logger.getLogger(ProductCommandRepository.class.getName());

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
    public boolean existsProductByIdOrTitle(String productId, String title) {
        var product = this.productRepositoryJpa.findByIdOrTitle(productId, title);
        if (product.isPresent()) {
            LOGGER.info("Product found: " + product.get());
        }
        return product.isPresent();
    }

    public Optional<ProductEntity> findByProductIdOrTitle(String id, String title) {
        return this.productRepositoryJpa.findByIdOrTitle(id, title);
    }

    @Override
    public Optional<Product> findProductById(String productId) {
        var productEntity = this.productRepositoryJpa.findById(productId);
        if (productEntity.isPresent()) {
            var product = productMapper.toDomain(productEntity.get());
            LOGGER.info("Product found: " + product);
            return Optional.of(product);
        }
        return Optional.empty();
    }

    @Override
    public boolean updateQuantityProduct(String productId, Integer newQuantity) {
        Optional<ProductEntity> existingProduct = this.productRepositoryJpa.findById(productId);

        if (existingProduct.isEmpty()) {
            LOGGER.warning("Attempted to update non-existent product with ID: " + productId);
            return false;
        }

        var productEntity = existingProduct.get();
        productEntity.setQuantity(newQuantity);

        this.productRepositoryJpa.save(productEntity);

        LOGGER.info("Product updated: " + productEntity);
        
        return true;
    }

}
