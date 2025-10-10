package com.luispiquinrey.cart.Command;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DeleteCartCommand {
    @TargetAggregateIdentifier
    private String idCart;
}
