package com.app.cqrs.shared.domain.ports;

import java.util.Optional;

import com.app.cqrs.shared.domain.User;
import com.app.cqrs.shared.domain.query.FetchUserPaymentDetailsQuery;

public interface IUserPaymentDetailGateway {

    Optional<User> findUserByPaymentDetails(FetchUserPaymentDetailsQuery detailsQuery);

}
