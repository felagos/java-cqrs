package com.app.cqrs.command.infrastructure.adapters.repositories.payment;

import org.springframework.data.repository.CrudRepository;

import com.app.cqrs.command.infrastructure.entities.PaymentEntity;

public interface PaymentRepositoryJpa extends CrudRepository<PaymentEntity, String> {

}
