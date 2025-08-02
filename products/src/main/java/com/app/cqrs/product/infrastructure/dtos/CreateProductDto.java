package com.app.cqrs.product.infrastructure.dtos;

import java.math.BigDecimal;

public record CreateProductDto (
    String title,
    BigDecimal price,
    Integer quantity
) {}