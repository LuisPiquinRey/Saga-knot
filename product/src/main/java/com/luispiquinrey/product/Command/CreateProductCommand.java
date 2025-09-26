package com.luispiquinrey.product.Command;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import com.luispiquinrey.product.Enums.Status;

import lombok.Builder;
import lombok.Data;

@Data 
@Builder
public class CreateProductCommand {
    @TargetAggregateIdentifier
    @Builder.Default
    private Long idProduct = null;
    
    private String name;
    private String brand;
    private Status status;
    private float price;
    private Integer stock;
}
