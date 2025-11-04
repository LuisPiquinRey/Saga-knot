package com.luispiquinrey.cart.Event;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ItemQuantityUpdatedEvent {
    private String idCart;
    private String idProduct;
    private int newQuantity;
    private float newTotal;
}
