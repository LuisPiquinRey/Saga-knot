package com.luispiquinrey.order.Command.Event;

import lombok.Data;

@Data
public class ItemReservedEvent {
    private String idItem;
    private String idProduct;
}
