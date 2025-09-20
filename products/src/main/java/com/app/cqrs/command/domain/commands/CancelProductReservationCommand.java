package com.app.cqrs.command.domain.commands;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class CancelProductReservationCommand {

    @TargetAggregateIdentifier
    private final String productId;
    private final String orderId;
    private final String userId;
    private final int quantity;
    private final String reason;

}
