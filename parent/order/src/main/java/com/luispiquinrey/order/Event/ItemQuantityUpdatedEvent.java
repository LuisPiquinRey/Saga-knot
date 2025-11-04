package com.luispiquinrey.order.Event;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ItemQuantityUpdatedEvent {
    private String idOrder;
    private String idProduct;
    private int newQuantity;
    private float newSubtotal;
}
