package com.luispiquinrey.order.Command;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import com.luispiquinrey.Enums.StatusOrder;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateOrderCommand {
    @TargetAggregateIdentifier
    private String idOrder;
    private String idUser;
    private String idAddress;
    private StatusOrder status;
    private String notes;
}
