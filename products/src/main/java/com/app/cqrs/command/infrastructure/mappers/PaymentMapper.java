package com.app.cqrs.command.infrastructure.mappers;

import org.springframework.stereotype.Component;

import com.app.cqrs.command.domain.Payment;
import com.app.cqrs.command.infrastructure.entities.PaymentEntity;

@Component
public class PaymentMapper {
    
    public PaymentEntity toEntity(Payment payment) {
        var paymentEntity = new PaymentEntity();
        
        paymentEntity.setPaymentId(payment.getPaymentId());
        paymentEntity.setOrderId(payment.getOrderId());

        return paymentEntity;
    }

    public Payment toDomain(PaymentEntity paymentEntity) {
        return new Payment(paymentEntity.getPaymentId(), paymentEntity.getOrderId());
    }
    
}
