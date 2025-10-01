package com.luispiquinrey.Command;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import com.luispiquinrey.Enums.Status;

import lombok.Builder;
import lombok.Data;

@Data 
@Builder
public class CreateProductCommand {
    @TargetAggregateIdentifier
    private String idProduct;
    
    private String name;
    private String brand;
    private Status status;
    private float price;
    private Integer stock;
}
