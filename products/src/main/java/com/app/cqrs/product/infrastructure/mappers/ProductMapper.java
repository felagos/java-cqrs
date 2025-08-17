package com.app.cqrs.product.infrastructure.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.app.cqrs.product.domain.commands.CreateProductCommand;
import com.app.cqrs.product.infrastructure.dtos.CreateProductDto;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    CreateProductDto toDto(CreateProductCommand product);
    
    @Mapping(target = "productId", expression = "java(java.util.UUID.randomUUID().toString())")
    CreateProductCommand toDomain(CreateProductDto dto);
}
