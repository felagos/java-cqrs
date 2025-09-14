package com.app.cqrs.command.domain.events;

import java.util.Optional;

import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

import com.app.cqrs.shared.domain.PaymentDetails;
import com.app.cqrs.shared.domain.User;
import com.app.cqrs.shared.domain.query.FetchUserPaymentDetailsQuery;

@Component
public class UserEventsHandler {

    @QueryHandler
    public Optional<User> on(FetchUserPaymentDetailsQuery query) {

        var paymentDetails = new PaymentDetails("123Card", "123", "Mi name", 12, 2030);
        var user = new User("FirstName", "LastName", query.getUserId(), paymentDetails);

        return Optional.of(user);
    }

}
