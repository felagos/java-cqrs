package com.app.cqrs.command.infrastructure.repositories.payment;

import com.app.cqrs.command.domain.Payment;
import com.app.cqrs.command.domain.ports.payments.IPaymentRepository;
import com.app.cqrs.command.infrastructure.mappers.PaymentMapper;

public class PaymentRepository implements IPaymentRepository {

    private final PaymentRepositoryJpa paymentRepositoryJpa;
    private final PaymentMapper paymentMapper;

    public PaymentRepository(PaymentRepositoryJpa paymentRepositoryJpa, PaymentMapper paymentMapper) {
        this.paymentRepositoryJpa = paymentRepositoryJpa;
        this.paymentMapper = paymentMapper;
    }

    @Override
    public Payment savePayment(Payment payment) {
        var paymentEntity = paymentMapper.toEntity(payment);

        var response = this.paymentRepositoryJpa.save(paymentEntity);

        return this.paymentMapper.toDomain(response);
    }

}
