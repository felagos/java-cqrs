package com.app.cqrs.command.domain.ports.payments;

import com.app.cqrs.command.domain.Payment;

public interface IPaymentRepository {

    Payment savePayment(Payment paymentEntity);

}
