package com.luispiquinrey.product.Aggregate;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

import com.luispiquinrey.product.Command.CreateCategoryCommand;
import com.luispiquinrey.product.Command.UpdateCategoryCommand;
import com.luispiquinrey.product.Event.CategoryCreatedEvent;
import com.luispiquinrey.product.Event.CategoryUpdatedEvent;

@Aggregate
public class CategoryAggregate {

    @AggregateIdentifier
    private String idCategory;

    private String name;

    private String description;

    private String image;

    public CategoryAggregate() {
    }

    @CommandHandler
    public CategoryAggregate(CreateCategoryCommand createCategoryCommand) {
        CategoryCreatedEvent categoryCreatedEvent = CategoryCreatedEvent.builder().build();
        BeanUtils.copyProperties(createCategoryCommand, categoryCreatedEvent);
        AggregateLifecycle.apply(categoryCreatedEvent);
    }

    @CommandHandler
    public CategoryAggregate(UpdateCategoryCommand updateCategoryCommand) {
        CategoryUpdatedEvent categoryUpdatedEvent = CategoryUpdatedEvent.builder().build();
        BeanUtils.copyProperties(updateCategoryCommand, categoryUpdatedEvent);
        AggregateLifecycle.apply(categoryUpdatedEvent);
    }

    @EventSourcingHandler
    public void on(CategoryCreatedEvent event) {
        this.idCategory = event.getIdCategory();
        this.name = event.getName();
        this.description = event.getDescription();
        this.image = event.getImage();
    }

    @EventSourcingHandler
    public void on(CategoryUpdatedEvent event) {
        this.idCategory = event.getIdCategory();
        this.name = event.getName();
        this.description = event.getDescription();
        this.image = event.getImage();
    }
}