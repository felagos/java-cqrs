package com.app.cqrs.query.infrastructure.repositories;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.ListCrudRepository;

import com.app.cqrs.shared.infrastructure.entities.ProductEntity;

public interface ProductQueryJpa extends ListCrudRepository<ProductEntity, String>, JpaSpecificationExecutor<ProductEntity> {

  

}
