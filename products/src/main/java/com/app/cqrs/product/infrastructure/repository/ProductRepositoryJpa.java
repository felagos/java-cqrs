package com.app.cqrs.product.infrastructure.repository;

import org.springframework.data.repository.CrudRepository;

import com.app.cqrs.product.infrastructure.entities.ProductEntity;

public interface ProductRepositoryJpa extends CrudRepository<ProductEntity, String> {
}
