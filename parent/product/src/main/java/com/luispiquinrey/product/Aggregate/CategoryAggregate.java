package com.luispiquinrey.product.Aggregate;

import lombok.extern.slf4j.Slf4j;
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

@Slf4j
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
        log.info("Handling CreateCategoryCommand for category: {}", command.getName());
        CategoryCreatedEvent event = CategoryCreatedEvent.builder().build();
        BeanUtils.copyProperties(command, event);
        AggregateLifecycle.apply(event);
        log.info("CategoryCreatedEvent applied successfully for category ID: {}", command.getIdCategory());
    }

    @CommandHandler
    public void handle(UpdateCategoryCommand command) {
        log.info("Handling UpdateCategoryCommand for category ID: {}", command.getIdCategory());
        CategoryUpdatedEvent event = CategoryUpdatedEvent.builder().build();
        BeanUtils.copyProperties(command, event);
        AggregateLifecycle.apply(event);
        log.info("CategoryUpdatedEvent applied successfully for category ID: {}", command.getIdCategory());
    }

    @CommandHandler
    public void handle(DeleteCategoryCommand command) {
        log.info("Handling DeleteCategoryCommand for category ID: {}", command.getIdCategory());
        CategoryDeletedEvent event = CategoryDeletedEvent.builder()
                .idCategory(command.getIdCategory())
                .build();
        AggregateLifecycle.apply(event);
        log.info("CategoryDeletedEvent applied successfully for category ID: {}", command.getIdCategory());
    }

    @EventSourcingHandler
    public void on(CategoryCreatedEvent event) {
        log.debug("Applying CategoryCreatedEvent for category ID: {}", event.getIdCategory());
        this.idCategory = event.getIdCategory();
        this.name = event.getName();
        this.description = event.getDescription();
        this.image = event.getImage();
        log.info("Category aggregate created - ID: {}, Name: {}", idCategory, name);
    }

    @EventSourcingHandler
    public void on(CategoryUpdatedEvent event) {
        log.debug("Applying CategoryUpdatedEvent for category ID: {}", event.getIdCategory());
        this.name = event.getName();
        this.description = event.getDescription();
        this.image = event.getImage();
        log.info("Category aggregate updated - ID: {}, Name: {}", idCategory, name);
    }

    @EventSourcingHandler
    public void on(CategoryDeletedEvent event) {
        log.info("Applying CategoryDeletedEvent - marking category ID: {} as deleted", event.getIdCategory());
        AggregateLifecycle.markDeleted();
        log.debug("Category aggregate deleted for ID: {}", event.getIdCategory());
    }
}
