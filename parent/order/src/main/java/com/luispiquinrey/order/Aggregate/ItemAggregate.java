package com.luispiquinrey.order.Aggregate;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

import com.luispiquinrey.Command.ReserveItemCommand;
import com.luispiquinrey.order.Command.Event.ItemReservedEvent;

@Aggregate
public class ItemAggregate {

    @AggregateIdentifier
    private String idItem;
    private String idProduct;

    @CommandHandler
    public ItemAggregate(ReserveItemCommand reserveItemCommand) {
        ItemReservedEvent itemReservedEvent=new ItemReservedEvent();
        BeanUtils.copyProperties(reserveItemCommand, itemReservedEvent);
        AggregateLifecycle.apply(itemReservedEvent);
    }

    @EventSourcingHandler
    public void on(ItemReservedEvent event) {
        this.idItem=event.getIdItem();
        this.idProduct=event.getIdProduct();
    }
}
