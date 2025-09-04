package com.app.cqrs.query.infrastructure.gateways;

import java.util.List;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.stereotype.Component;

import com.app.cqrs.query.domain.ports.IProductQueryGateway;
import com.app.cqrs.query.domain.ProductFilter;
import com.app.cqrs.query.domain.queries.FilterProductQuery;
import com.app.cqrs.query.domain.queries.FindProductQuery;
import com.app.cqrs.shared.domain.Product;

@Component
public class ProductQueryGateway implements IProductQueryGateway {

    private final QueryGateway queryGateway;

    public ProductQueryGateway(QueryGateway queryGateway) {
        this.queryGateway = queryGateway;
    }

    @Override
    public List<Product> findAll() {
        return this.queryGateway.query(
                new FindProductQuery(),
                ResponseTypes.multipleInstancesOf(Product.class))
                .join();
    }

    @Override
    public List<Product> findByFilter(ProductFilter filter) {
        return this.queryGateway.query(
                new FilterProductQuery(filter),
                ResponseTypes.multipleInstancesOf(Product.class))
                .join();
    }

}
