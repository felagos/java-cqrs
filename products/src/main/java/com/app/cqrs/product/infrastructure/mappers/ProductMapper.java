package com.app.cqrs.product.infrastructure.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.app.cqrs.product.domain.Product;
import com.app.cqrs.product.infrastructure.dtos.CreateProductDto;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    CreateProductDto toDto(Product product);
    
    @Mapping(target = "productId", expression = "java(java.util.UUID.randomUUID().toString())")
    Product toDomain(CreateProductDto dto);
}
