package com.app.cqrs.command.domain.events.payments;

import lombok.Value;

@Value
public class PaymentProcessedEvent {

    String paymentId;
    String orderId;

}
