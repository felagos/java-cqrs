package com.app.cqrs.command.infrastructure.repositories.payment;

import org.springframework.data.repository.CrudRepository;

import com.app.cqrs.command.infrastructure.entities.PaymentEntity;

public interface PaymentRepositoryJpa extends CrudRepository<PaymentEntity, String> {

}
