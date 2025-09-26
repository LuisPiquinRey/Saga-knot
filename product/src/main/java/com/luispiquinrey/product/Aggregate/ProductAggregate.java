package com.luispiquinrey.product.Aggregate;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

import com.luispiquinrey.product.Command.CreateProductCommand;
import com.luispiquinrey.product.Command.Event.ProductCreatedEvent;
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
    public ProductAggregate(CreateProductCommand createProductCommand) {
        if (createProductCommand.getName() == null || createProductCommand.getName().isBlank()) {
            throw new IllegalArgumentException("Product name cannot be null or empty");
        }

        if (createProductCommand.getBrand() == null || createProductCommand.getBrand().isBlank()) {
            throw new IllegalArgumentException("Product brand cannot be null or empty");
        }

        if (createProductCommand.getPrice() < 0) {
            throw new IllegalArgumentException("Product price cannot be negative");
        }

        if (createProductCommand.getStock() < 0) {
            throw new IllegalArgumentException("Product stock cannot be negative");
        }
        ProductCreatedEvent productCreatedEvent = new ProductCreatedEvent();
        BeanUtils.copyProperties(createProductCommand, productCreatedEvent);
        AggregateLifecycle.apply(productCreatedEvent);
    }
}
