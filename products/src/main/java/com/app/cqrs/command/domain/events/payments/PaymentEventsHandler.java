package com.app.cqrs.command.domain.events.payments;

import java.util.logging.Logger;

import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;
import com.app.cqrs.command.domain.mapper.PaymentDomainMapper;
import com.app.cqrs.command.domain.ports.payments.IPaymentRepository;

@Component
public class PaymentEventsHandler {

    private final Logger logger = Logger.getLogger(PaymentEventsHandler.class.getName());

    private final IPaymentRepository paymentRepository;
    private final PaymentDomainMapper paymentDomainMapper;

    public PaymentEventsHandler(IPaymentRepository paymentRepository, PaymentDomainMapper paymentDomainMapper) {
        this.paymentRepository = paymentRepository;
        this.paymentDomainMapper = paymentDomainMapper;
    }

    @EventHandler
    public void handlePaymentProcessedEvent(PaymentProcessedEvent event) {
        logger.info("PaymentProcessedEvent is called for orderId: " + event.getOrderId());

        var payment = this.paymentDomainMapper.eventToDomain(event);

        this.paymentRepository.savePayment(payment);

    }

}
