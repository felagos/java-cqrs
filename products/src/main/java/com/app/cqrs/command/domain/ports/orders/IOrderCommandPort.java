package com.app.cqrs.command.domain.ports.orders;

import org.axonframework.commandhandling.CommandCallback;

import com.app.cqrs.command.domain.Order;
import com.app.cqrs.command.domain.commands.ApproveOrderCommand;
import com.app.cqrs.command.domain.commands.CancelProductReservationCommand;
import com.app.cqrs.command.domain.commands.CreateOrderCommand;
import com.app.cqrs.shared.domain.commands.ProcessPaymentCommand;
import com.app.cqrs.shared.domain.commands.ReserveProductCommand;

public interface IOrderCommandPort {

    Order createOrder(CreateOrderCommand order);
    void sendReservation(ReserveProductCommand event, CommandCallback<ReserveProductCommand, Object> callback);
    String sendPayment(ProcessPaymentCommand payment);
    String sendApprovedPayment(ApproveOrderCommand command);
    String sendCancelReservation(CancelProductReservationCommand command);

    void sendPaymentAsync(ProcessPaymentCommand payment, CommandCallback<ProcessPaymentCommand, Object> callback);
    void sendApprovedPaymentAsync(ApproveOrderCommand command, CommandCallback<ApproveOrderCommand, Object> callback);

}
