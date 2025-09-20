package com.app.cqrs.command.infrastructure.gateways;

import java.util.concurrent.TimeUnit;

import org.axonframework.commandhandling.CommandCallback;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.stereotype.Component;

import com.app.cqrs.command.domain.Order;
import com.app.cqrs.command.domain.commands.ApproveOrderCommand;
import com.app.cqrs.command.domain.commands.CancelProductReservationCommand;
import com.app.cqrs.command.domain.commands.CreateOrderCommand;
import com.app.cqrs.command.domain.ports.orders.IOrderCommandPort;
import com.app.cqrs.shared.domain.commands.ProcessPaymentCommand;
import com.app.cqrs.shared.domain.commands.ReserveProductCommand;

@Component
public class OrderCommandGateway implements IOrderCommandPort {

    private final CommandGateway commandGateway;

    public OrderCommandGateway(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @Override
    public Order createOrder(CreateOrderCommand order) {
        String response = this.commandGateway.sendAndWait(order);

        return new Order(response, order.getProductId(), order.getUserId(), order.getQuantity(), order.getAddressId(),
                order.getOrderStatus());
    }

    @Override
    public void sendReservation(ReserveProductCommand event, CommandCallback<ReserveProductCommand, Object> callback) {
        this.commandGateway.send(event, callback);
    }

    @Override
    public String sendPayment(ProcessPaymentCommand payment) {
        return this.commandGateway.sendAndWait(payment, 10, TimeUnit.SECONDS);
    }

    @Override
    public String sendApprovedPayment(ApproveOrderCommand command) {
        return this.commandGateway.sendAndWait(command);
    }

    @Override
    public void sendPaymentAsync(ProcessPaymentCommand payment,
            CommandCallback<ProcessPaymentCommand, Object> callback) {
        this.commandGateway.send(payment, callback);
    }

    @Override
    public void sendApprovedPaymentAsync(ApproveOrderCommand command,
            CommandCallback<ApproveOrderCommand, Object> callback) {
        this.commandGateway.send(command, callback);
    }

    @Override
    public String sendCancelReservation(CancelProductReservationCommand command) {
        return this.commandGateway.sendAndWait(command);
    }

}
