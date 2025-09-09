package com.app.cqrs.command.infrastructure.repositories.product;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.app.cqrs.shared.infrastructure.entities.ProductEntity;

public interface ProductRepositoryJpa extends CrudRepository<ProductEntity, String> {

    Optional<ProductEntity> findByIdOrTitle(String id, String title);

    Optional<ProductEntity> findByTitle(String title);

}
