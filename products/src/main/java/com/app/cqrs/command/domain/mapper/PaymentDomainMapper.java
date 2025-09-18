package com.app.cqrs.command.domain.mapper;

import org.springframework.stereotype.Component;

import com.app.cqrs.command.domain.Payment;
import com.app.cqrs.command.domain.events.payments.PaymentProcessedEvent;

@Component
public class PaymentDomainMapper {

    public Payment eventToDomain(PaymentProcessedEvent event) {
        return new Payment(event.getPaymentId(), event.getOrderId());
    }

}
