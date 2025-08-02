package com.app.cqrs.product.infrastructure.mappers;

import org.mapstruct.Mapper;

import com.app.cqrs.product.domain.Product;
import com.app.cqrs.product.infrastructure.dtos.CreateProductDto;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    CreateProductDto toDto(Product product);
    Product toDomain(CreateProductDto dto);
}
