package com.luispiquinrey.Command;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddProductToCartCommand {
    @TargetAggregateIdentifier
    private String idCart;
    private String idItem;
    private float total;
    private int quantity;
    private String name;
    private String brand;
}
