package com.luispiquinrey.order.Command.Event;
import com.luispiquinrey.order.Enums.Status;

import lombok.Data;

@Data
public class OrderCreatedEvent {
    private Long idOrder;

    private Status status;
}
