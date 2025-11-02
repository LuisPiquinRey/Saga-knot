package com.luispiquinrey.user.Command;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DeleteAddressCommand {
    @TargetAggregateIdentifier
    private String idAddress;
}
