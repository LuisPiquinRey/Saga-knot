package com.luispiquinrey.Event;
import java.util.ArrayList;

import org.axonframework.eventsourcing.EventSourcingHandler;

import com.luispiquinrey.DTO.ItemCompact;
import com.luispiquinrey.Enums.StatusCart;

import lombok.Data;

@Data
public class AddedProductToCartEvent {
    private String idCart;
    private String idItem;
    private float total;
    private int quantity;
}
