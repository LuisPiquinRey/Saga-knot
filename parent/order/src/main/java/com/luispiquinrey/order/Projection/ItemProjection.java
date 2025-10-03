package com.luispiquinrey.order.Projection;

import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.luispiquinrey.order.Command.Event.ItemReservedEvent;
import com.luispiquinrey.order.Command.Event.OrderCreatedEvent;
import com.luispiquinrey.order.Entities.Item;
import com.luispiquinrey.order.Entities.Order;
import com.luispiquinrey.order.Repository.RepositoryItem;

@Component
public class ItemProjection {
    private final RepositoryItem repositoryItem;

    public ItemProjection(RepositoryItem repositoryItem){
        this.repositoryItem=repositoryItem;
    }
    @EventHandler
    public void on(ItemReservedEvent itemReservedEvent){
        Item item=new Item();
        BeanUtils.copyProperties(itemReservedEvent,item);
        repositoryItem.save(item);
    }
}
