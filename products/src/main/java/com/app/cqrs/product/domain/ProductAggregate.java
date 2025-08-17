package com.app.cqrs.product.domain;

import java.math.BigDecimal;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.spring.stereotype.Aggregate;
import com.app.cqrs.product.domain.commands.CreateProductCommand;

@Aggregate
public class ProductAggregate {

    public ProductAggregate() {
    }

    @CommandHandler
    public ProductAggregate(CreateProductCommand command) {
        if (command.price().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Invalid product price");
        }
    }

}
