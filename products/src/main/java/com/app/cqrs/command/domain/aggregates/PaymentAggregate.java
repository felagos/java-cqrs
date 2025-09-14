package com.app.cqrs.command.domain.aggregates;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import com.app.cqrs.command.domain.events.payments.PaymentProcessedEvent;
import com.app.cqrs.command.domain.exceptions.InvalidPaymentException;
import com.app.cqrs.shared.domain.commands.ProcessPaymentCommand;

@Aggregate
public class PaymentAggregate {

    @AggregateIdentifier
    private String paymentId;
    private String orderId;

    public PaymentAggregate() {
    }

    @CommandHandler
    public PaymentAggregate(ProcessPaymentCommand processPaymentCommand) {

        if (processPaymentCommand.getPaymentDetails() == null) {
            throw new InvalidPaymentException("Missing payment details");
        }

        if (processPaymentCommand.getOrderId() == null) {
            throw new InvalidPaymentException("Missing orderId");
        }

        if (processPaymentCommand.getPaymentId() == null) {
            throw new InvalidPaymentException("Missing paymentId");
        }

        var paymentProcessedEvent = new PaymentProcessedEvent(processPaymentCommand.getPaymentId(),
                processPaymentCommand.getOrderId());

        AggregateLifecycle.apply(paymentProcessedEvent);
    }

    @EventSourcingHandler
    protected void on(PaymentProcessedEvent paymentProcessedEvent) {
        this.paymentId = paymentProcessedEvent.getPaymentId();
        this.orderId = paymentProcessedEvent.getOrderId();
    }
}