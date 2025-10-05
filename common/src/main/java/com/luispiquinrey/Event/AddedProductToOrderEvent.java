package com.luispiquinrey.Event;
import java.util.ArrayList;

import org.axonframework.eventsourcing.EventSourcingHandler;

import com.luispiquinrey.DTO.ItemCompact;
import com.luispiquinrey.Enums.StatusOrder;

import lombok.Data;

@Data
public class AddedProductToOrderEvent {
    private String idOrder;
    private String idItem;
    private float total;
    private int quantity;
}
