package com.luispiquinrey.user.Command;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UploadImageUserCommand {
    @TargetAggregateIdentifier
    private String username;
    private String key;
    private String imageUrl;
}
