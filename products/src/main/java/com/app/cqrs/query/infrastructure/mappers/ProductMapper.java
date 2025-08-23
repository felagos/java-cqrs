package com.app.cqrs.query.infrastructure.mappers;

import java.util.List;

import org.springframework.stereotype.Component;

import com.app.cqrs.query.domain.Product;
import com.app.cqrs.query.infrastructure.dtos.ProductDto;

@Component
public class ProductMapper {

    public Product toDomain(ProductDto dto) {
        return new Product(dto.id(), dto.title(), dto.price(), dto.quantity());
    }

    public ProductDto toDto(Product domain) {
        return new ProductDto(domain.id(), domain.title(), domain.price(), domain.quantity());
    }

    public List<ProductDto> toDtoList(List<Product> domains) {
        return domains.stream()
                .map(this::toDto)
                .toList();
    }

}
