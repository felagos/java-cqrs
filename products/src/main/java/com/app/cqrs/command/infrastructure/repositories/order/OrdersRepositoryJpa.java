package com.app.cqrs.command.infrastructure.repositories.order;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.app.cqrs.command.domain.OrderStatus;
import com.app.cqrs.command.infrastructure.entities.OrderEntity;

public interface OrdersRepositoryJpa extends CrudRepository<OrderEntity, String> {

    @Modifying
    @Transactional
    @Query("UPDATE OrderEntity o SET o.orderStatus = :orderStatus WHERE o.orderId = :orderId")
    int updateOrderStatusById(@Param("orderId") String orderId, @Param("orderStatus") OrderStatus orderStatus);

}
