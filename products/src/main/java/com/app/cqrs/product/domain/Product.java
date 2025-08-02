package com.app.cqrs.product.domain;

import java.math.BigDecimal;

public record Product(
        String title,
        BigDecimal price,
        Integer quantity
    ) { }
