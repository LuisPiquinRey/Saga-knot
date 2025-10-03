package com.luispiquinrey.Command;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReserveItemCommand {
    @TargetAggregateIdentifier
    private String idItem;
    private String idProduct;
}
