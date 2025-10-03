package com.luispiquinrey.Command;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import lombok.Builder;
import lombok.Data;

@Data 
@Builder
public class RequestAddToOrderProductCommand {
    @TargetAggregateIdentifier
    private String idProduct;
    private String idOrder;
    private float price;
    private Integer stock;
}
