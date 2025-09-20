package com.app.cqrs.shared.domain.commands;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import com.app.cqrs.shared.domain.PaymentDetails;
import lombok.Value;

@Value
public class ProcessPaymentCommand {

    @TargetAggregateIdentifier
    String paymentId;
    String orderId;
    PaymentDetails paymentDetails;

}
