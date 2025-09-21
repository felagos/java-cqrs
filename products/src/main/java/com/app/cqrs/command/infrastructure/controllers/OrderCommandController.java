package com.app.cqrs.command.infrastructure.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.app.cqrs.command.application.OrderCommandService;
import com.app.cqrs.shared.infrastructure.dtos.OrderCreateDto;
import com.app.cqrs.shared.infrastructure.dtos.OrderCreatedDto;
import com.app.cqrs.shared.infrastructure.mappers.OrderMapper;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/orders")
public class OrderCommandController {

    private final OrderCommandService orderCommandService;
    private final OrderMapper orderMapper;

    public OrderCommandController(OrderCommandService orderCommandService, OrderMapper orderMapper) {
        this.orderCommandService = orderCommandService;
        this.orderMapper = orderMapper;
    }

    @PostMapping
    public OrderCreatedDto createOrder(@Valid @RequestBody OrderCreateDto order) {
        var command = this.orderMapper.toDomain(order);
        var response = this.orderCommandService.createOrder(command);

        return this.orderMapper.toDto(response);
    }

}
