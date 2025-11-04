package com.luispiquinrey.cart.Event;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreatedCartEvent {
    private String idCart;
    private String idUser;
}
