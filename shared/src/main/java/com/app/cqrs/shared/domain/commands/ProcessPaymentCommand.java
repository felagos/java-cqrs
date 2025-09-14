package com.app.cqrs.shared.domain.commands;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import com.app.cqrs.shared.domain.PaymentDetails;

public class ProcessPaymentCommand {

    @TargetAggregateIdentifier
    private final String paymentId;
    private final String orderId;
    private final PaymentDetails paymentDetails;

    public ProcessPaymentCommand(String paymentId, String orderId, PaymentDetails paymentDetails) {
        this.paymentId = paymentId;
        this.orderId = orderId;
        this.paymentDetails = paymentDetails;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public String getOrderId() {
        return orderId;
    }

    public PaymentDetails getPaymentDetails() {
        return paymentDetails;
    }

}
