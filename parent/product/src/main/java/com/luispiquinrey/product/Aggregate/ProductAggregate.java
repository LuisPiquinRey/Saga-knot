package com.luispiquinrey.product.Aggregate;

import java.util.List;

import com.luispiquinrey.product.Repository.RepositoryProductLookup;
import com.luispiquinrey.product.Service.BrandService;
import com.luispiquinrey.product.Service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.serialization.Revision;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;
import com.luispiquinrey.common.Enums.StatusProduct;
import com.luispiquinrey.product.Command.CreateProductCommand;
import com.luispiquinrey.product.Command.DeleteProductCommand;
import com.luispiquinrey.product.Command.UpdateProductCommand;
import com.luispiquinrey.product.Entities.Brand;
import com.luispiquinrey.product.Entities.Category;
import com.luispiquinrey.product.Entities.Gender;
import com.luispiquinrey.product.Event.ProductCreatedEvent;
import com.luispiquinrey.product.Event.ProductDeletedEvent;
import com.luispiquinrey.product.Event.ProductUpdatedEvent;

@Slf4j
@Aggregate(snapshotTriggerDefinition = "productSnapshotTrigger")
@Revision("1")
public class ProductAggregate {

    @AggregateIdentifier
    private String idProduct;

    private String name;

    private Gender gender;

    private StatusProduct status=StatusProduct.CREATED;

    private float price;

    private Integer stock;

    public ProductAggregate() {
    }

    @CommandHandler
    public ProductAggregate(CreateProductCommand command, BrandService brandService, CategoryService categoryService, RepositoryProductLookup repositoryProductLookup) {
        log.debug("Creating ProductCreatedEvent for product: {}", command.getName());
        ProductCreatedEvent event = ProductCreatedEvent.builder().build();
        BeanUtils.copyProperties(command, event);
        AggregateLifecycle.apply(event);
        log.info("ProductCreatedEvent applied successfully for product ID: {}", command.getIdProduct());
    }
    @CommandHandler
    public void handle(UpdateProductCommand command, BrandService brandService, CategoryService categoryService, RepositoryProductLookup repositoryProductLookup) {
        log.debug("Creating ProductUpdatedEvent for product ID: {}", command.getIdProduct());
        ProductUpdatedEvent productUpdatedEvent=ProductUpdatedEvent.builder().build();
        BeanUtils.copyProperties(command,productUpdatedEvent);
        AggregateLifecycle.apply(productUpdatedEvent);
        log.info("ProductUpdatedEvent applied successfully for product ID: {}", command.getIdProduct());
    }

    @CommandHandler
    public void handle(DeleteProductCommand deleteProductCommand, RepositoryProductLookup repositoryProductLookup) {
        log.info("Handling DeleteProductCommand for product ID: {}", deleteProductCommand.getIdProduct());
        ProductDeletedEvent productDeletedEvent=ProductDeletedEvent.builder()
                .idProduct(deleteProductCommand.getIdProduct())
                .build();
        AggregateLifecycle.apply(productDeletedEvent);
        log.info("ProductDeletedEvent applied successfully for product ID: {}", deleteProductCommand.getIdProduct());
    }

    @EventSourcingHandler
    public void on(ProductCreatedEvent event) {
        log.debug("Applying ProductCreatedEvent for product ID: {}", event.getIdProduct());
        this.idProduct = event.getIdProduct();
        this.name = event.getName();
        this.gender= event.getGender();
        this.price = event.getPrice();
        this.stock = event.getStock();
        log.info("Product aggregate state updated - ID: {}, Name: {}, Price: {}, Stock: {}",
                idProduct, name, price, stock);
    }
    @EventSourcingHandler
    public void on(ProductUpdatedEvent event) {
        log.debug("Applying ProductUpdatedEvent for product ID: {}", event.getIdProduct());
        this.idProduct = event.getIdProduct();
        this.name = event.getName();
        this.gender= event.getGender();
        this.price = event.getPrice();
        this.stock = event.getStock();
        this.status=event.getStatus();
        log.info("Product aggregate updated - ID: {}, Name: {}, Status: {}, Price: {}, Stock: {}",
                idProduct, name, status, price, stock);
    }
    @EventSourcingHandler
    public void on(ProductDeletedEvent event){
        log.info("Applying ProductDeletedEvent - marking product ID: {} as deleted", event.getIdProduct());
        AggregateLifecycle.markDeleted();
        log.debug("Product aggregate deleted for ID: {}", event.getIdProduct());
    }
}
