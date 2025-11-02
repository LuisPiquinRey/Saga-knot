package com.luispiquinrey.user.Command;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import lombok.Builder;
import lombok.Data;

@Data 
@Builder
public class DeleteUserCommand {
    @TargetAggregateIdentifier
    private String username;
}
