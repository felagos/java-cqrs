package com.app.cqrs.command.infrastructure.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.app.cqrs.command.infrastructure.entities.ProductLookupEntity;

public interface ProductLookupJpa extends CrudRepository<ProductLookupEntity, String> {

    Optional<ProductLookupEntity> findByIdOrTitle(String id, String title);

}