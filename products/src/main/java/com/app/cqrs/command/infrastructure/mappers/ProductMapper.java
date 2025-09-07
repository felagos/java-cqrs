package com.app.cqrs.command.infrastructure.mappers;

import java.util.UUID;

import org.springframework.stereotype.Component;

import com.app.cqrs.command.domain.commands.CreateProductCommand;
import com.app.cqrs.command.domain.events.ProductCreatedEvent;
import com.app.cqrs.command.infrastructure.dtos.CreateProductDto;
import com.app.cqrs.shared.infrastructure.entities.ProductEntity;

@Component("commandProductMapper")
public class ProductMapper {
    public CreateProductDto toDto(CreateProductCommand product) {
        var createProduct = new CreateProductDto();
        createProduct.setTitle(product.getTitle());
        createProduct.setPrice(product.getPrice());
        createProduct.setQuantity(product.getQuantity());
        return createProduct;
    }

    public CreateProductCommand toDomain(CreateProductDto dto) {
        return new CreateProductCommand(
                UUID.randomUUID().toString(),
                dto.getTitle(),
                dto.getPrice(),
                dto.getQuantity());
    }

    public ProductEntity toEntity(ProductCreatedEvent product) {
        var entity = new ProductEntity();
        entity.setId(product.getProductId());
        entity.setTitle(product.getTitle());
        entity.setPrice(product.getPrice());
        entity.setQuantity(product.getQuantity());
        return entity;
    }

    public ProductCreatedEvent toEvent(ProductEntity entity) {
        return new ProductCreatedEvent(
                entity.getId(),
                entity.getTitle(),
                entity.getPrice(),
                entity.getQuantity());
    }
}
