package com.luispiquinrey.order.Command.Event;
import java.util.ArrayList;

import org.axonframework.eventsourcing.EventSourcingHandler;

import com.luispiquinrey.order.Entities.Item;
import com.luispiquinrey.order.Enums.Status;

import lombok.Data;

@Data
public class OrderCreatedEvent {
    private String idOrder;
    private Status status;
    private float total;
    private int quantity;
    private ArrayList<Item> items;
}
