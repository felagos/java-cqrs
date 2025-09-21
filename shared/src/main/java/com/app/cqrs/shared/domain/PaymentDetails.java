package com.app.cqrs.shared.domain;

import lombok.Value;

@Value
public class PaymentDetails {
    String cardNumber;
    String ccv;
    String name;
    Integer validUntilMonth;
    Integer validUntilYear;
}
