package com.luispiquinrey.order.Command;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RemoveItemFromOrderCommand {
    @TargetAggregateIdentifier
    private String idOrder;
    private String idProduct;
}
