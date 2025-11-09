package com.luispiquinrey.product.Aggregate;

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
        BrandCreatedEvent event = BrandCreatedEvent.builder().build();
        BeanUtils.copyProperties(command, event);
        AggregateLifecycle.apply(event);
    }

    @CommandHandler
    public void handle(UpdateBrandCommand command) {
        BrandUpdatedEvent event = BrandUpdatedEvent.builder().build();
        BeanUtils.copyProperties(command, event);
        AggregateLifecycle.apply(event);
    }

    @CommandHandler
    public void handle(DeleteBrandCommand command) {
        BrandDeletedEvent event = BrandDeletedEvent.builder()
                .idBrand(command.getIdBrand())
                .build();
        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on(BrandCreatedEvent event) {
        this.idBrand = event.getIdBrand();
        this.name = event.getName();
        this.description = event.getDescription();
    }

    @EventSourcingHandler
    public void on(BrandUpdatedEvent event) {
        this.name = event.getName();
        this.description = event.getDescription();
    }

    @EventSourcingHandler
    public void on(BrandDeletedEvent event) {
        AggregateLifecycle.markDeleted();
    }
}
