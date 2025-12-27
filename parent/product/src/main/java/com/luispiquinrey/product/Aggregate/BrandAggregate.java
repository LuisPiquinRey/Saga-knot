package com.luispiquinrey.product.Aggregate;

import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

import com.luispiquinrey.product.Command.CreateBrandCommand;
import com.luispiquinrey.product.Command.DeleteBrandCommand;
import com.luispiquinrey.product.Command.UpdateBrandCommand;
import com.luispiquinrey.product.Event.BrandCreatedEvent;
import com.luispiquinrey.product.Event.BrandDeletedEvent;
import com.luispiquinrey.product.Event.BrandUpdatedEvent;

@Slf4j
@Aggregate
public class BrandAggregate {

    @AggregateIdentifier
    private String idBrand;

    private String name;

    private String description;

    public BrandAggregate() {
    }

    @CommandHandler
    public BrandAggregate(CreateBrandCommand command) {
        log.info("Handling CreateBrandCommand for brand: {}", command.getName());
        BrandCreatedEvent event = BrandCreatedEvent.builder().build();
        BeanUtils.copyProperties(command, event);
        AggregateLifecycle.apply(event);
        log.info("BrandCreatedEvent applied successfully for brand ID: {}", command.getIdBrand());
    }

    @CommandHandler
    public void handle(UpdateBrandCommand command) {
        log.info("Handling UpdateBrandCommand for brand ID: {}", command.getIdBrand());
        BrandUpdatedEvent event = BrandUpdatedEvent.builder().build();
        BeanUtils.copyProperties(command, event);
        AggregateLifecycle.apply(event);
        log.info("BrandUpdatedEvent applied successfully for brand ID: {}", command.getIdBrand());
    }

    @CommandHandler
    public void handle(DeleteBrandCommand command) {
        log.info("Handling DeleteBrandCommand for brand ID: {}", command.getIdBrand());
        BrandDeletedEvent event = BrandDeletedEvent.builder()
                .idBrand(command.getIdBrand())
                .build();
        AggregateLifecycle.apply(event);
        log.info("BrandDeletedEvent applied successfully for brand ID: {}", command.getIdBrand());
    }

    @EventSourcingHandler
    public void on(BrandCreatedEvent event) {
        log.debug("Applying BrandCreatedEvent for brand ID: {}", event.getIdBrand());
        this.idBrand = event.getIdBrand();
        this.name = event.getName();
        this.description = event.getDescription();
        log.info("Brand aggregate created - ID: {}, Name: {}", idBrand, name);
    }

    @EventSourcingHandler
    public void on(BrandUpdatedEvent event) {
        log.debug("Applying BrandUpdatedEvent for brand ID: {}", event.getIdBrand());
        this.name = event.getName();
        this.description = event.getDescription();
        log.info("Brand aggregate updated - ID: {}, Name: {}", idBrand, name);
    }

    @EventSourcingHandler
    public void on(BrandDeletedEvent event) {
        log.info("Applying BrandDeletedEvent - marking brand ID: {} as deleted", event.getIdBrand());
        AggregateLifecycle.markDeleted();
        log.debug("Brand aggregate deleted for ID: {}", event.getIdBrand());
    }
}
