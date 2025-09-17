package com.app.cqrs.shared.domain;

public class User {

    private String firstName;
    private String lastName;
    private String userId;
    private PaymentDetails paymentDetails;

    public User(String firstName, String lastName, String userId, PaymentDetails paymentDetails) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userId = userId;
        this.paymentDetails = paymentDetails;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public PaymentDetails getPaymentDetails() {
        return paymentDetails;
    }

    public void setPaymentDetails(PaymentDetails paymentDetails) {
        this.paymentDetails = paymentDetails;
    }

    @Override
    public String toString() {
        return "User [firstName=" + firstName + ", lastName=" + lastName + ", userId=" + userId + ", paymentDetails="
                + paymentDetails + "]";
    }

}
