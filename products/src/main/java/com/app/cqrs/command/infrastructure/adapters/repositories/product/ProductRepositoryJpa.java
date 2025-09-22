package com.app.cqrs.command.infrastructure.adapters.repositories.product;

import java.util.Optional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.app.cqrs.shared.infrastructure.entities.ProductEntity;

public interface ProductRepositoryJpa extends CrudRepository<ProductEntity, String> {

    Optional<ProductEntity> findByIdOrTitle(String id, String title);

    Optional<ProductEntity> findByTitle(String title);

    @Modifying
    @Transactional
    @Query("UPDATE ProductEntity p SET p.quantity = :newQuantity WHERE p.id = :productId")
    int updateProductQuantity(@Param("productId") String productId, @Param("newQuantity") Integer newQuantity);

    @Modifying
    @Transactional
    @Query("UPDATE ProductEntity p SET p.quantity = p.quantity - :decrementBy WHERE p.id = :productId AND p.quantity >= :decrementBy")
    int decrementProductQuantity(@Param("productId") String productId, @Param("decrementBy") Integer decrementBy);

    @Modifying
    @Transactional
    @Query("UPDATE ProductEntity p SET p.quantity = p.quantity + :incrementBy WHERE p.id = :productId")
    int incrementProductQuantity(@Param("productId") String productId, @Param("incrementBy") Integer incrementBy);

}
