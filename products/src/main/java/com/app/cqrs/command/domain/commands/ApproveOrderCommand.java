package com.app.cqrs.command.domain.commands;

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
