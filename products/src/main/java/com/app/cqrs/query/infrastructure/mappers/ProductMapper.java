package com.app.cqrs.query.infrastructure.mappers;

import java.util.List;

import org.springframework.stereotype.Component;

import com.app.cqrs.query.domain.Product;
import com.app.cqrs.query.infrastructure.dtos.ProductDto;
import com.app.cqrs.query.infrastructure.entities.ProductEntity;

@Component("queryProductMapper")
public class ProductMapper {

    public Product toDomain(ProductDto dto) {
    return new Product(dto.getId(), dto.getTitle(), dto.getPrice(), dto.getQuantity());
    }

    public ProductDto toDto(Product domain) {
    return new ProductDto(domain.getId(), domain.getTitle(), domain.getPrice(), domain.getQuantity());
    }

    public List<ProductDto> toDtoList(List<Product> domains) {
        return domains.stream()
                .map(this::toDto)
                .toList();
    }

    public List<Product> toDomainList(List<ProductEntity> dtos) {
        return dtos.stream()
                .map(entity -> {
                    return new Product(entity.getId(), entity.getTitle(), entity.getPrice(), entity.getQuantity());
                })
                .toList();
    }

}
