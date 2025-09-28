package com.luispiquinrey.order.Command;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import com.luispiquinrey.order.Enums.Status;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateOrderCommand {
    @TargetAggregateIdentifier
    private Long idOrder;

    private Status status;
}
