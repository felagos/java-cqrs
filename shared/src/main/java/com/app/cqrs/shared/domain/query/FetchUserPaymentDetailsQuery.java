package com.app.cqrs.shared.domain.query;

public class FetchUserPaymentDetailsQuery {

    private String userId;

    public FetchUserPaymentDetailsQuery() {
    }

    public FetchUserPaymentDetailsQuery(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "FetchUserPaymentDetailsQuery [userId=" + userId + "]";
    }

}
