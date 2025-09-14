package com.app.cqrs.command.domain;

public class Payment {

    private final String paymentId;
    private final String orderId;

    public Payment(String paymentId, String orderId) {
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
