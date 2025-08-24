package com.app.cqrs.query.infrastructure.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.app.cqrs.query.infrastructure.entities.ProductEntity;

public interface ProductQueryJpa extends MongoRepository<ProductEntity, String> {

}
