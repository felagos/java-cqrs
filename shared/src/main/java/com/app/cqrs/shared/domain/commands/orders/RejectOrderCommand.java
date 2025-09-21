package com.app.cqrs.shared.domain.commands.orders;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class RejectOrderCommand {

    @TargetAggregateIdentifier
    private final String orderId;
    private final String reason;

    @Override
    public String toString() {
        return "RejectOrderCommand [orderId=" + orderId + ", reason=" + reason + "]";
    }

}