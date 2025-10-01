package com.luispiquinrey.product.Aggregate;

import java.util.UUID;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

import com.luispiquinrey.Command.CreateProductCommand;
import com.luispiquinrey.Enums.Status;
import com.luispiquinrey.product.Event.ProductCreatedEvent;

@Aggregate
public class ProductAggregate {

    @AggregateIdentifier
    private String productId;

    private String name;

    private String brand;

    private Status status;

    private float price;

    private Integer stock;

    public ProductAggregate() {
    }

    @CommandHandler
    public ProductAggregate(CreateProductCommand createProductCommand) {
        ProductCreatedEvent productCreatedEvent = new ProductCreatedEvent();
        BeanUtils.copyProperties(createProductCommand, productCreatedEvent);
        AggregateLifecycle.apply(productCreatedEvent);
    }

    @EventSourcingHandler
    public void on(ProductCreatedEvent event) {
        this.productId = event.getIdProduct();
        this.name = event.getName();
        this.brand = event.getBrand();
        this.status = event.getStatus();
        this.price = event.getPrice();
        this.stock = event.getStock();
    }
}
