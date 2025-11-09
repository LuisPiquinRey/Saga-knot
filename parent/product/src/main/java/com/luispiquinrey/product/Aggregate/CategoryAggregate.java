package com.luispiquinrey.product.Aggregate;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

import com.luispiquinrey.product.Command.CreateCategoryCommand;
import com.luispiquinrey.product.Command.DeleteCategoryCommand;
import com.luispiquinrey.product.Command.UpdateCategoryCommand;
import com.luispiquinrey.product.Event.CategoryCreatedEvent;
import com.luispiquinrey.product.Event.CategoryDeletedEvent;
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
    public CategoryAggregate(CreateCategoryCommand command) {
        CategoryCreatedEvent event = CategoryCreatedEvent.builder().build();
        BeanUtils.copyProperties(command, event);
        AggregateLifecycle.apply(event);
    }

    @CommandHandler
    public void handle(UpdateCategoryCommand command) {
        CategoryUpdatedEvent event = CategoryUpdatedEvent.builder().build();
        BeanUtils.copyProperties(command, event);
        AggregateLifecycle.apply(event);
    }

    @CommandHandler
    public void handle(DeleteCategoryCommand command) {
        CategoryDeletedEvent event = CategoryDeletedEvent.builder()
                .idCategory(command.getIdCategory())
                .build();
        AggregateLifecycle.apply(event);
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
        this.name = event.getName();
        this.description = event.getDescription();
        this.image = event.getImage();
    }

    @EventSourcingHandler
    public void on(CategoryDeletedEvent event) {
        AggregateLifecycle.markDeleted();
    }
}
