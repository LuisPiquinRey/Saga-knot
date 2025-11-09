package com.luispiquinrey.product.Aggregate;

import java.util.List;
import java.util.UUID;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;
import com.luispiquinrey.Enums.StatusProduct;
import com.luispiquinrey.product.Command.CreateProductCommand;
import com.luispiquinrey.product.Command.DeleteProductCommand;
import com.luispiquinrey.product.Command.UpdateProductCommand;
import com.luispiquinrey.product.Entities.Brand;
import com.luispiquinrey.product.Entities.Category;
import com.luispiquinrey.product.Entities.Gender;
import com.luispiquinrey.product.Event.ProductCreatedEvent;
import com.luispiquinrey.product.Event.ProductDeletedEvent;
import com.luispiquinrey.product.Event.ProductUpdatedEvent;

@Aggregate
public class ProductAggregate {

    @AggregateIdentifier
    private String idProduct;

    private String name;

    private Brand brand;

    private List<Category> categories;

    private Gender gender;

    private StatusProduct status=StatusProduct.CREATED;

    private float price;

    private Integer stock;

    public ProductAggregate() {
    }

    @CommandHandler
    public ProductAggregate(CreateProductCommand createProductCommand) {
        ProductCreatedEvent productCreatedEvent = ProductCreatedEvent.builder().build();
        BeanUtils.copyProperties(createProductCommand, productCreatedEvent);
        AggregateLifecycle.apply(productCreatedEvent);
    }
    @CommandHandler
    public void handle(UpdateProductCommand updateProductCommand){
        ProductUpdatedEvent productUpdatedEvent=ProductUpdatedEvent.builder().build();
        BeanUtils.copyProperties(updateProductCommand,productUpdatedEvent);
        AggregateLifecycle.apply(productUpdatedEvent);
    }

    @CommandHandler
    public void handle(DeleteProductCommand deleteProductCommand){
        ProductDeletedEvent productDeletedEvent=ProductDeletedEvent.builder()
                .idProduct(deleteProductCommand.getIdProduct())
                .build();
        AggregateLifecycle.apply(productDeletedEvent);
    }

    @EventSourcingHandler
    public void on(ProductCreatedEvent event) {
        this.idProduct = event.getIdProduct();
        this.name = event.getName();
        this.brand= event.getBrand();
        this.categories= event.getCategories();
        this.gender= event.getGender();
        this.price = event.getPrice();
        this.stock = event.getStock();
    }
    @EventSourcingHandler
    public void on(ProductUpdatedEvent event) {
        this.idProduct = event.getIdProduct();
        this.name = event.getName();
        this.brand= event.getBrand();
        this.categories= event.getCategories();
        this.gender= event.getGender();
        this.price = event.getPrice();
        this.stock = event.getStock();
        this.status=event.getStatus();
    }
    @EventSourcingHandler
    public void on(ProductDeletedEvent event){
        AggregateLifecycle.markDeleted();
    }
}
