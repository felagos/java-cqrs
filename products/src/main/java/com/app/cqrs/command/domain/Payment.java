package com.app.cqrs.command.domain;

import lombok.Value;

@Value
public class Payment {
    String paymentId;
    String orderId;
}
