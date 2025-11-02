package com.luispiquinrey.product.Aggregate;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.modelling.command.TargetAggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

import com.luispiquinrey.product.Command.CreateBrandCommand;
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
    public BrandAggregate(CreateBrandCommand createBrandCommand) {
        BrandCreatedEvent brandCreatedEvent = BrandCreatedEvent.builder().build();
        BeanUtils.copyProperties(createBrandCommand, brandCreatedEvent);
        AggregateLifecycle.apply(brandCreatedEvent);
    }

    @CommandHandler
    public BrandAggregate(UpdateBrandCommand updateBrandCommand) {
        BrandUpdatedEvent brandUpdatedEvent = BrandUpdatedEvent.builder().build();
        BeanUtils.copyProperties(updateBrandCommand, brandUpdatedEvent);
        AggregateLifecycle.apply(brandUpdatedEvent);
    }

    @EventSourcingHandler
    public void on(BrandCreatedEvent event) {
        this.idBrand = event.getIdBrand();
        this.name = event.getName();
        this.description = event.getDescription();
    }

    @EventSourcingHandler
    public void on(BrandUpdatedEvent event) {
        this.idBrand = event.getIdBrand();
        this.name = event.getName();
        this.description = event.getDescription();
    }
    @EventSourcingHandler
    public void on(BrandDeletedEvent event){
        AggregateLifecycle.markDeleted();
    }
}