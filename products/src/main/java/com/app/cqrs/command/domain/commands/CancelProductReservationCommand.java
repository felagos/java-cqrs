package com.app.cqrs.command.domain.commands;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class CancelProductReservationCommand {

    @TargetAggregateIdentifier
    private final String productId;
    private final String orderId;
    private final String userId;
    private final int quantity;
    private final String reason;

    @Override
    public String toString() {
        return "CancelProductReservationCommand [productId=" + productId + ", orderId=" + orderId + ", userId=" + userId
                + ", quantity=" + quantity + ", reason=" + reason + "]";
    }

}
