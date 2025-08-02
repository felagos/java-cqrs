package com.app.cqrs.product.infrastructure.dtos;

import java.math.BigDecimal;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CreateProductDto (
    @NotEmpty(message = "Title cannot be empty")
    String title,

    @NotNull(message = "Price cannot be null")
    @Positive(message = "Price must be positive")
    BigDecimal price,

    @NotNull(message = "Quantity cannot be null")
    @Positive(message = "Quantity must be positive")
    Integer quantity
) {}