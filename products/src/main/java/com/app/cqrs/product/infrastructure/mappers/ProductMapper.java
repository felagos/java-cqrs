package com.app.cqrs.product.infrastructure.mappers;

import org.springframework.stereotype.Component;
import com.app.cqrs.product.domain.commands.CreateProductCommand;
import com.app.cqrs.product.infrastructure.dtos.CreateProductDto;

@Component
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
                java.util.UUID.randomUUID().toString(),
                dto.getTitle(),
                dto.getPrice(),
                dto.getQuantity());
    }
}
