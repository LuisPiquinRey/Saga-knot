package com.luispiquinrey.order.Command;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddItemToOrderCommand {
    @TargetAggregateIdentifier
    private String idOrder;
    private String idProduct;
    private int quantity;
    private float pricePerUnit;
    private float discount;
}
