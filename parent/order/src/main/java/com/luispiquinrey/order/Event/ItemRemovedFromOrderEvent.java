package com.luispiquinrey.order.Event;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ItemRemovedFromOrderEvent {
    private String idOrder;
    private String idProduct;
}
