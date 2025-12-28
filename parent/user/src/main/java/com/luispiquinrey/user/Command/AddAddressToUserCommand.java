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
    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    private String username;

    @NotBlank(message = "Street is required")
    @Size(min = 3, max = 150, message = "Street must be between 3 and 150 characters")
    private String street;

    @NotBlank(message = "City is required")
    @Size(min = 2, max = 100, message = "City must be between 2 and 100 characters")
    private String city;

    @Size(min = 2, max = 100, message = "State must be between 2 and 100 characters")
    private String state;

    @NotBlank(message = "Country is required")
    @Size(min = 2, max = 100, message = "Country must be between 2 and 100 characters")
    private String country;

    @Pattern(regexp = "^[A-Za-z0-9\\-\\s]{3,12}$", message = "Postal code must be alphanumeric, 3 to 12 characters (spaces and hyphens allowed)")
    private String postalCode;
}