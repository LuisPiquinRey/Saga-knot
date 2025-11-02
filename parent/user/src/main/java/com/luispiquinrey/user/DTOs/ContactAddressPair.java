package com.luispiquinrey.user.DTOs;

import com.luispiquinrey.user.Entities.Address;
import com.luispiquinrey.user.Entities.Contact;

public record ContactAddressPair(Contact contact, Address address) {
}
