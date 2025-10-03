package com.luispiquinrey.order.Command.Event;
import java.util.ArrayList;

import org.axonframework.eventsourcing.EventSourcingHandler;

import com.luispiquinrey.DTO.ItemCompact;
import com.luispiquinrey.Enums.StatusOrder;
import com.luispiquinrey.order.Entities.Item;

import lombok.Data;

@Data
public class OrderCreatedEvent {
    private String idOrder;
    private StatusOrder status;
    private float total;
    private int quantity;
    private String idProduct;
}
