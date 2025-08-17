package com.app.cqrs.product.domain.commands;

import java.math.BigDecimal;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

public record CreateProductCommand(
        @TargetAggregateIdentifier String productId,
        String title,
        BigDecimal price,
        Integer quantity) {
}
