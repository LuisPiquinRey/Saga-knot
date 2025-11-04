package com.luispiquinrey.order.Event;

import com.luispiquinrey.Enums.StatusOrder;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderCreatedEvent {
    private String idOrder;
    private String idUser;
    private String idAddress;
    private StatusOrder status;
    private String notes;
}
