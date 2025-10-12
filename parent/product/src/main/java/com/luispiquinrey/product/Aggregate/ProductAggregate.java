package com.luispiquinrey.product.Aggregate;

import java.util.UUID;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;
import com.luispiquinrey.Enums.StatusProduct;
import com.luispiquinrey.product.Command.CreateProductCommand;
import com.luispiquinrey.product.Command.UpdateProductCommand;
import com.luispiquinrey.product.Event.ProductCreatedEvent;
import com.luispiquinrey.product.Event.ProductUpdatedEvent;

@Aggregate
public class ProductAggregate {

    @AggregateIdentifier
    private String idProduct;

    private String name;

    private String brand;

    private StatusProduct status=StatusProduct.CREATED;

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
    @CommandHandler
    public ProductAggregate(UpdateProductCommand updateProductCommand){
        ProductUpdatedEvent productUpdatedEvent=new ProductUpdatedEvent();
        BeanUtils.copyProperties(updateProductCommand,productUpdatedEvent);
        AggregateLifecycle.apply(productUpdatedEvent);
    }

    @EventSourcingHandler
    public void on(ProductCreatedEvent event) {
        this.idProduct = event.getIdProduct();
        this.name = event.getName();
        this.brand = event.getBrand();
        this.price = event.getPrice();
        this.stock = event.getStock();
    }
    @EventSourcingHandler
    public void on(ProductUpdatedEvent event) {
        this.idProduct = event.getIdProduct();
        this.name = event.getName();
        this.brand = event.getBrand();
        this.price = event.getPrice();
        this.stock = event.getStock();
        this.status=event.getStatus();
    }
}
