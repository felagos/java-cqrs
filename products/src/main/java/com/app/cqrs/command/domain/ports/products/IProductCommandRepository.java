package com.app.cqrs.command.domain.ports.products;

import java.util.Optional;

import com.app.cqrs.command.domain.events.products.ProductCreatedEvent;
import com.app.cqrs.shared.domain.Product;

public interface IProductCommandRepository {
    void saveProduct(ProductCreatedEvent product);

    boolean existsProductByIdOrTitle(String id, String title);

    Optional<Product> findProductById(String productId);

    boolean updateQuantityProduct(String productId, Integer newQuantity);

    boolean decrementQuantityProduct(String productId, Integer decrementBy);

    boolean incrementQuantityProduct(String productId, Integer incrementBy);

    void deleteAllProducts();
}
