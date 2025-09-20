package com.app.cqrs.shared.domain.commands;

import org.axonframework.modelling.command.TargetAggregateIdentifier;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReserveProductCommand {

    @TargetAggregateIdentifier
    private String productId;
    private int quantity;
    private String orderId;
    private String userId;

}
