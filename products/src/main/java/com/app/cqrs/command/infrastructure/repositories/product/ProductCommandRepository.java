package com.app.cqrs.command.infrastructure.repositories.product;

import java.util.Optional;
import java.util.logging.Logger;

import org.springframework.stereotype.Repository;

import com.app.cqrs.command.domain.events.products.ProductCreatedEvent;
import com.app.cqrs.command.domain.ports.products.IProductCommandRepository;
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
        int updatedRows = this.productRepositoryJpa.updateProductQuantity(productId, newQuantity);
        
        if (updatedRows == 0) {
            LOGGER.warning("Attempted to update non-existent product with ID: " + productId);
            return false;
        }
        
        LOGGER.info("Product quantity updated for ID: " + productId + " to quantity: " + newQuantity);
        
        return true;
    }

    @Override
    public boolean decrementQuantityProduct(String productId, Integer decrementBy) {
        int updatedRows = this.productRepositoryJpa.decrementProductQuantity(productId, decrementBy);
        
        if (updatedRows == 0) {
            LOGGER.warning("Failed to decrement quantity for product ID: " + productId + ". Product not found or insufficient quantity.");
            return false;
        }
        
        LOGGER.info("Product quantity decremented by " + decrementBy + " for product ID: " + productId);
        
        return true;
    }

    @Override
    public boolean incrementQuantityProduct(String productId, Integer incrementBy) {
        int updatedRows = this.productRepositoryJpa.incrementProductQuantity(productId, incrementBy);
        
        if (updatedRows == 0) {
            LOGGER.warning("Failed to increment quantity for product ID: " + productId + ". Product not found.");
            return false;
        }
        
        LOGGER.info("Product quantity incremented by " + incrementBy + " for product ID: " + productId);
        
        return true;
    }

}
