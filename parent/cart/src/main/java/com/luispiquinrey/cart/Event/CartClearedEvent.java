package com.luispiquinrey.cart.Event;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CartClearedEvent {
    private String idCart;
}
