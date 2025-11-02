package com.luispiquinrey.user.Command;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateAddressCommand {
    @TargetAggregateIdentifier
    private String idAddress;
    private String street;
    private String postalCode;
    private String city;
    private String state;
    private String country;
    private Long idContact;
}
