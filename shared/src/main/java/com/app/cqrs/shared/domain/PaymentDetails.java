package com.app.cqrs.shared.domain;

public class PaymentDetails {

    private final String cardNumber;
    private final String ccv;
    private final String name;
    private final Integer validUntilMonth;
    private final Integer validUntilYear;

    public PaymentDetails(String cardNumber, String ccv, String name, Integer validUntilMonth, Integer validUntilYear) {
        this.cardNumber = cardNumber;
        this.ccv = ccv;
        this.name = name;
        this.validUntilMonth = validUntilMonth;
        this.validUntilYear = validUntilYear;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getCcv() {
        return ccv;
    }

    public String getName() {
        return name;
    }

    public Integer getValidUntilMonth() {
        return validUntilMonth;
    }

    public Integer getValidUntilYear() {
        return validUntilYear;
    }

}
