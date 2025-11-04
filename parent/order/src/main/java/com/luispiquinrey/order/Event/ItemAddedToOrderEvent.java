package com.luispiquinrey.order.Event;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ItemAddedToOrderEvent {
    private String idOrder;
    private String idProduct;
    private int quantity;
    private float pricePerUnit;
    private float discount;
    private float subtotal;
}
