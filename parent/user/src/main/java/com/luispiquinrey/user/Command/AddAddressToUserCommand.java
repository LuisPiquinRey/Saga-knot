package com.luispiquinrey.user.Command;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddAddressToUserCommand {
    @TargetAggregateIdentifier
    private String username;
    private String street;
    private String city;
    private String state;
    private String country;
    private String postalCode;
}
