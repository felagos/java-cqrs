package com.app.cqrs.shared.domain.commands.orders;

import org.axonframework.modelling.command.TargetAggregateIdentifier;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApproveOrderCommand {

    @TargetAggregateIdentifier
    private String orderId;

}