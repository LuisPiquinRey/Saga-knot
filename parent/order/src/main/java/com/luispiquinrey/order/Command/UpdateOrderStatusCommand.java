package com.luispiquinrey.order.Command;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import com.luispiquinrey.common.Enums.StatusOrder;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateOrderStatusCommand {
    @TargetAggregateIdentifier
    private String idOrder;
    private StatusOrder status;
}
