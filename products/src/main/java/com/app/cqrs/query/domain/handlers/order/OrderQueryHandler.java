package com.app.cqrs.query.domain.handlers.order;

import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

import com.app.cqrs.shared.domain.ports.orders.IOrderRepository;
import com.app.cqrs.shared.domain.query.orders.FindOrderQuery;

@Component
public class OrderQueryHandler {

    private final IOrderRepository orderRepository;

    public OrderQueryHandler(IOrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @QueryHandler
    public void findOrder(FindOrderQuery query) {
        this.orderRepository.findOrderById(query.getOrderId());
    }

}
