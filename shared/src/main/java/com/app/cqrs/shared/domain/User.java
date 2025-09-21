package com.app.cqrs.shared.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User {
    private String firstName;
    private String lastName;
    private String userId;
    private PaymentDetails paymentDetails;
}
