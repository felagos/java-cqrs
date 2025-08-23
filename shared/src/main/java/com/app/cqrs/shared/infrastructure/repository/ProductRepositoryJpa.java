package com.app.cqrs.shared.infrastructure.repository;

import java.util.Optional;

import com.app.cqrs.shared.infrastructure.entities.ProductEntity;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepositoryJpa extends CrudRepository<ProductEntity, String> {

    Optional<ProductEntity> findByIdOrTitle(String id, String title);
    
}
