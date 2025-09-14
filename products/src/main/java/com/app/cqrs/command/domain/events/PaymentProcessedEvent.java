package com.app.cqrs.command.domain.events;

public class PaymentProcessedEvent {

    private final String paymentId;
    private final String orderId;

    public PaymentProcessedEvent(String paymentId, String orderId) {
        this.paymentId = paymentId;
        this.orderId = orderId;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public String getOrderId() {
        return orderId;
    }

}
