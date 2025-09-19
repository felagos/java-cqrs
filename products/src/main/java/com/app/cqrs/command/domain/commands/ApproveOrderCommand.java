package com.app.cqrs.command.domain.commands;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

public class ApproveOrderCommand {

    @TargetAggregateIdentifier
    private String orderId;

    public ApproveOrderCommand() {
    }

    public ApproveOrderCommand(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderId() {
        return orderId;
    }

    @Override
    public String toString() {
        return "ApproveOrderCommand [orderId=" + orderId + "]";
    }

}
