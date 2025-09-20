package com.app.cqrs.command.domain.commands;

import java.math.BigDecimal;

import org.axonframework.modelling.command.TargetAggregateIdentifier;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateProductCommand {

    @TargetAggregateIdentifier
    private String productId;
    private String title;
    private BigDecimal price;
    private Integer quantity;

}
