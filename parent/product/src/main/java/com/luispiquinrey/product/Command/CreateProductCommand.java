package com.luispiquinrey.product.Command;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateProductCommand {
    @TargetAggregateIdentifier
    private String idProduct;
    private String name;
    private String idBrand;
    private float price;
    private Integer stock;
}
