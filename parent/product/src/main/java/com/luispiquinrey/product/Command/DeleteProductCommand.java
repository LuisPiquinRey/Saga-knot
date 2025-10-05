package com.luispiquinrey.product.Command;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DeleteProductCommand {
    @TargetAggregateIdentifier
    private String idProduct;
}
