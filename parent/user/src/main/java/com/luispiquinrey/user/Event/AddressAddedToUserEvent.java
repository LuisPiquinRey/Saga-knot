package com.luispiquinrey.user.Event;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddressAddedToUserEvent {
    private String idAddress;
    private String street;
    private String postalCode;
    private String city;
    private String state;
    private String country;
    private String username;
}
