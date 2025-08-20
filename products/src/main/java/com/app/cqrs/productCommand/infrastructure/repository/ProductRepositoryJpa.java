package com.app.cqrs.productCommand.infrastructure.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.app.cqrs.productCommand.infrastructure.entities.ProductEntity;

public interface ProductRepositoryJpa extends CrudRepository<ProductEntity, String> {

    Optional<ProductEntity> findByIdOrTitle(String id, String title);
    
}
