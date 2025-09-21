package com.app.cqrs.shared.infrastructure.repositories.orders;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.app.cqrs.shared.domain.orders.OrderStatus;
import com.app.cqrs.shared.infrastructure.entities.OrderEntity;

public interface OrdersRepositoryJpa extends CrudRepository<OrderEntity, String> {

    @Modifying
    @Transactional
    @Query("UPDATE OrderEntity o SET o.orderStatus = :orderStatus WHERE o.orderId = :orderId")
    int updateOrderStatusById(@Param("orderId") String orderId, @Param("orderStatus") OrderStatus orderStatus);

}