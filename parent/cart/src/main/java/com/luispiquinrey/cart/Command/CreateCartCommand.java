package com.luispiquinrey.cart.Command;

import java.util.UUID;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateCartCommand {
    @TargetAggregateIdentifier
    private String idCart=UUID.randomUUID().toString();
    private String idUser;
}
