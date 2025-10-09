package com.luispiquinrey.user.Command;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import lombok.Builder;
import lombok.Data;

@Data 
@Builder
public class UpdateUserCommand {
    @TargetAggregateIdentifier
    private Long idContact;
    private String username;
    private String email;
    private String password;
    private String phoneNumber;
    private String profileImage;
}
