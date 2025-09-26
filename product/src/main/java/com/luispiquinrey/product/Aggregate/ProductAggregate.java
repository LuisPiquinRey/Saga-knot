package com.luispiquinrey.product.Aggregate;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

import com.luispiquinrey.product.Command.CreateProductCommand;
import com.luispiquinrey.product.Enums.Status;

@Aggregate
public class ProductAggregate {
    @AggregateIdentifier
    private Long productId;

    private String name;

    private String brand;

    private Status status;

    private float price;

    private Integer stock;

    public ProductAggregate() {
    }

    @CommandHandler
    public ProductAggregate(CreateProductCommand createProductCommand){

    }
}
