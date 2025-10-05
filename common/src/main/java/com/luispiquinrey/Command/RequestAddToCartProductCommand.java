package com.luispiquinrey.Command;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import lombok.Builder;
import lombok.Data;

@Data 
@Builder
public class RequestAddToCartProductCommand {
    @TargetAggregateIdentifier
    private String idProduct;
    private String idCart;
    private float price;
    private Integer stock;
}
