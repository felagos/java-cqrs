package com.app.cqrs.shared.infrastructure.gateways;

import java.util.Optional;

import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.stereotype.Component;
import com.app.cqrs.shared.domain.User;
import com.app.cqrs.shared.domain.ports.IUserPaymentDetailGateway;
import com.app.cqrs.shared.domain.query.FetchUserPaymentDetailsQuery;

@Component
public class UserPaymentDetailGateway implements IUserPaymentDetailGateway {

	private final QueryGateway queryGateway;

	public UserPaymentDetailGateway(QueryGateway queryGateway) {
		this.queryGateway = queryGateway;
	}

	@Override
	public Optional<User> findUserByPaymentDetails(FetchUserPaymentDetailsQuery detailsQuery) {
		var response = this.queryGateway.query(detailsQuery, ResponseTypes.multipleInstancesOf(User.class))
				.join();

		return response.stream().findFirst();
	}
}
