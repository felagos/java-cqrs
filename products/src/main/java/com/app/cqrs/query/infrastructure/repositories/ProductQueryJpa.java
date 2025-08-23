package com.app.cqrs.query.infrastructure.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.app.cqrs.shared.infrastructure.entities.ProductEntity;

public interface ProductQueryJpa extends CrudRepository<ProductEntity, String> {

    public List<ProductEntity> findAll();

}
