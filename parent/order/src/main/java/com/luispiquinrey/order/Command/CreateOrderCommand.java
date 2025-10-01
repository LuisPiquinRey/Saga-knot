package com.luispiquinrey.order.Command;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import com.luispiquinrey.order.Enums.Status;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateOrderCommand {
    @TargetAggregateIdentifier
    private String idOrder;
    private Status status;
    private float total;
    private int quantity;
}
