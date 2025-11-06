package com.luispiquinrey.cart.Command;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddItemToCartCommand {
    @TargetAggregateIdentifier
    private String idCart;
    private String idProduct;
    private int quantity;
    private float pricePerUnit;
}
