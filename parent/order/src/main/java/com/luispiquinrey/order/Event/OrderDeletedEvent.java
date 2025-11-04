package com.luispiquinrey.order.Event;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderDeletedEvent {
    private String idOrder;
}
