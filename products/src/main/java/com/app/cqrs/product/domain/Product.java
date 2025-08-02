package com.app.cqrs.product.domain;

import java.math.BigDecimal;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

public record Product(
        @TargetAggregateIdentifier String productId,
        String title,
        BigDecimal price,
        Integer quantity) {
}
