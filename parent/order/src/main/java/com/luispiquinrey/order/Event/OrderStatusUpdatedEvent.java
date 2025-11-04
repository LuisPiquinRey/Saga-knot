package com.luispiquinrey.order.Event;

import com.luispiquinrey.Enums.StatusOrder;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderStatusUpdatedEvent {
    private String idOrder;
    private StatusOrder status;
}
