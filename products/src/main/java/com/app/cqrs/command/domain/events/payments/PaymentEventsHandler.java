package com.app.cqrs.command.domain.events.payments;

import java.util.logging.Logger;

import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

import com.app.cqrs.command.domain.Payment;
import com.app.cqrs.command.domain.ports.payments.IPaymentRepository;

@Component
public class PaymentEventsHandler {

    private final Logger logger = Logger.getLogger(PaymentEventsHandler.class.getName());

    private final IPaymentRepository paymentRepository;

    public PaymentEventsHandler(IPaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @EventHandler
    public void on(PaymentProcessedEvent event) {
        logger.info("PaymentProcessedEvent is called for orderId: " + event.getOrderId());

        var payment = new Payment(event.getPaymentId(), event.getOrderId());

        this.paymentRepository.savePayment(payment);

    }

}
