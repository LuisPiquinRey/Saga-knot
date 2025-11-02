package com.luispiquinrey.user.Command;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

public class UpdateAddressCommand {
    @TargetAggregateIdentifier
    private String idAddress;
    private String street;
    private String postalCode;
    private String city;
    private String state;
    private String country;
    private Long idContact;
}
