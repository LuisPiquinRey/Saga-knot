package com.luispiquinrey.order.Command.Event;
import org.axonframework.eventsourcing.EventSourcingHandler;

import com.luispiquinrey.order.Enums.Status;

import lombok.Data;

@Data
public class OrderCreatedEvent {
    private String idOrder;

    private Status status;

}
